package sqlcomponentizer.dbserializer;

import sqlcomponentizer.dbserializer.exception.DBSerializerException;
import sqlcomponentizer.dbserializer.exception.DBDeserializerPrimaryKeyMissingException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBSerializer {

    public static Object getPrimaryKey(Object dbObject) throws DBSerializerException, IllegalAccessException, DBDeserializerPrimaryKeyMissingException {
        // Check if DBSerializable
        DBSerializationValidator.checkSerializable(dbObject);

        // Create an object for primaryKey which will be used to check if there are multiple primary keys and for the return value if there are no primary keys
        Object primaryKey = null;

        // Check each declared field until one is found with DBColumn.isPrimaryKey()
        for (Field field: dbObject.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(DBColumn.class)) {
                if (field.getAnnotation(DBColumn.class).isPrimaryKey()) {
                    if (primaryKey != null)
                        throw new DBSerializerException("Multiple primary keys found for object " + dbObject);

                    primaryKey = field.get(dbObject);
                }
            }
        }

        throw new DBDeserializerPrimaryKeyMissingException("No primary key found when trying to getPrimaryKey in DBSerializer!");
    }

    public static String getPrimaryKeyName(Class<?> dbClass) throws DBSerializerException, DBDeserializerPrimaryKeyMissingException {
        // Check if DBSerializable
        DBSerializationValidator.checkSerializable(dbClass);

        // Create an object for primaryKeyName which will be used to check if there are multiple primary keys and for the return value if there are no primary keys
        String primaryKeyName = null;

        // Check each declared field until one is found with DBColumn.isPrimaryKey()
        for (Field field: dbClass.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(DBColumn.class)) {
                DBColumn annotation = field.getAnnotation(DBColumn.class);

                if (annotation.isPrimaryKey()) {
                    if (primaryKeyName != null)
                        throw new DBSerializerException("Multiple primary keys found for object " + dbClass);

                    primaryKeyName = annotation.name();
                }
            }
        }

        return primaryKeyName;
    }

    public static List<String> getForeignKeyNames(Class<?> dbClass) throws DBSerializerException {
        // Check if DBSerializable
        DBSerializationValidator.checkSerializable(dbClass);

        // Create foreignKeyNames list
        List<String> foreignKeyNames = new ArrayList<>();

        // Check each declared field until one is found with DBColumn.isForeignKey()
        for (Field field: dbClass.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(DBColumn.class)) {
                DBColumn annotation = field.getAnnotation(DBColumn.class);
                if (annotation.isForeignKey()) {
                    // Either add foreignKeyName from foreignKeyReferecnes in annotation if not blank or from field name if blank
                    if (!annotation.foreignKeyReferences().isBlank())
                        foreignKeyNames.add(annotation.foreignKeyReferences());
                    else
                        foreignKeyNames.add(annotation.name());
                }
            }
        }

        return foreignKeyNames;
    }

    public static String getTableName(Class<?> dbClass) throws DBSerializerException {
        // Check if class is serializable
        DBSerializationValidator.checkSerializable(dbClass);

        return dbClass.getAnnotation(DBSerializable.class).tableName();
    }

    public static Map<String, Object> getTableMap(Object dbObject) throws IllegalAccessException, DBSerializerException, InvocationTargetException {
        // Check if DBSerializable
        DBSerializationValidator.checkSerializable(dbObject);

        // Create table map
        Map<String, Object> tableMap = new HashMap<>();

        // Check each declared field for DBColumn annotation and map its name property the key to the field's object
        for (Field field: dbObject.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(DBColumn.class)) {
                String key = field.getAnnotation(DBColumn.class).name();
                Object value = field.get(dbObject);

                // If value is not null and is enum, set the value to the value from DBGetterValue
                if (value != null && value.getClass().isEnum()) {
                    value = getDBEnumGetterValue(value);
                }

                // Put the key and value in tableMap
                tableMap.put(key, value);
            }
        }

        return tableMap;
    }

    public static List<Object> getSubObjects(Object dbObject) throws DBSerializerException, IllegalAccessException {
        // Check if DBSerializable
        DBSerializationValidator.checkSerializable(dbObject);

        // Create sub object list
        List<Object> subObjects = new ArrayList<>();

        // Check each declared field for DBSubObject annotation and add each field's object to the subObjects list
        for (Field field: dbObject.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(DBSubObject.class)) {
                field.setAccessible(true);

                subObjects.add(field.get(dbObject));
            }
        }

        return subObjects;
    }

    public static List<Class<?>> getSubObjectClasses(Class<?> dbClass) throws DBSerializerException {
        // Check if DBSerializable
        DBSerializationValidator.checkSerializable(dbClass);

        // Create sub object class list
        List<Class<?>> subObjectClasses = new ArrayList<>();

        // Check each declared field for DBSubObject annotation and add each field's class to the subObjectClasses list
        for (Field field: dbClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(DBSubObject.class)) {
                subObjectClasses.add(field.getType());
            }
        }

        return subObjectClasses;
    }

    public static Class<?> getSubObjectClass(Class<?> dbClass, String name) throws DBSerializerException {
        // Check if DBSerializable
        DBSerializationValidator.checkSerializable(dbClass);

        // Check each declared field for DBSubObject annotation and return the getType of the first field that's name matches name
        for (Field field: dbClass.getDeclaredFields())
            if (field.isAnnotationPresent(DBSubObject.class))
                if (field.getName().equals(name))
                    return field.getType();

        throw new DBSerializerException("Could not find subObject class for given name–" + name + "–in given dbClass–" + dbClass + "–!");
    }

    public static List<String> getSubObjectNames(Class<?> dbClass) throws DBSerializerException {
        // Check if DBSerializable
        DBSerializationValidator.checkSerializable(dbClass);

        // Create sub object name list
        List<String> subObjectNames = new ArrayList<>();

        // Check each declared field for DBSubObject annotation and add each field's name to the subObjectClasses list
        for (Field field: dbClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(DBSubObject.class)) {
                subObjectNames.add(field.getName());
            }
        }

        return subObjectNames;
    }

    private static Object getDBEnumGetterValue(Object toGetObject) throws InvocationTargetException, IllegalAccessException {
        // Loop through methods in enum until DBEnumGetter annotation is found
        for (Method enumMethod: toGetObject.getClass().getDeclaredMethods()) {
            // If getter annotation is present on a method, use the value returned by that method as the value
            if (enumMethod.isAnnotationPresent(DBEnumGetter.class)) {
                return enumMethod.invoke(toGetObject);
            }
        }

        return null;
    }

}
