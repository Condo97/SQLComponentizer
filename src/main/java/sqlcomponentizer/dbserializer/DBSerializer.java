package sqlcomponentizer.dbserializer;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBSerializer {

    public static Object getPrimaryKey(Object dbObject) throws DBSerializerException, IllegalAccessException, DBSerializerPrimaryKeyMissingException {
        // Check if DBSerializable
        DBSerializationValidator.checkSerializable(dbObject);

        // Check each declared field until one is found with DBColumn.primaryKey() == true
        for (Field field: dbObject.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(DBColumn.class)) {
                if (field.getAnnotation(DBColumn.class).primaryKey())
                    return field.get(dbObject);
            }
        }

        throw new DBSerializerPrimaryKeyMissingException("No primary key found when trying to getPrimaryKey in DBSerializer!");
    }

    public static String getPrimaryKeyName(Class<?> dbClass) throws DBSerializerException, DBSerializerPrimaryKeyMissingException {
        DBSerializationValidator.checkSerializable(dbClass);
        Field[] var1 = dbClass.getDeclaredFields();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            Field field = var1[var3];
            field.setAccessible(true);
            if (field.isAnnotationPresent(DBColumn.class)) {
                if (field.getAnnotation(DBColumn.class).primaryKey())
                    return field.getAnnotation(DBColumn.class).name();
            }
        }

        throw new DBSerializerPrimaryKeyMissingException("No primary key found when trying to getPrimaryKey in DBSerializer!");
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
            field.setAccessible(true);
            if (field.isAnnotationPresent(DBSubObject.class)) {
                subObjects.add(field.get(dbObject));
            }
        }

        return subObjects;
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
