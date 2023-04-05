package sqlcomponentizer.dbserializer;

import java.lang.reflect.Field;
import java.util.HashMap;
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

    public static String getPrimaryKeyName(Object dbObject) throws DBSerializerException, IllegalAccessException, DBSerializerPrimaryKeyMissingException {
        DBSerializationValidator.checkSerializable(dbObject);
        Field[] var1 = dbObject.getClass().getDeclaredFields();
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

    public static String getTableName(Object dbObject) throws DBSerializerException {
        if (dbObject == null) throw new DBSerializerException("Cannot get table name of null object " + dbObject);
        return getTableName(dbObject.getClass());
    }

    public static String getTableName(Class<?> dbClass) throws DBSerializerException {
        // Check if class is serializable
        DBSerializationValidator.checkSerializable(dbClass);

        return dbClass.getAnnotation(DBSerializable.class).tableName();
    }

    public static Map<String, Object> getTableMap(Object dbObject) throws IllegalAccessException, DBSerializerException {
        // Check if DBSerializable
        DBSerializationValidator.checkSerializable(dbObject);

        Class<?> tableClass = dbObject.getClass();
        Map<String, Object> map = new HashMap<>();

        // Check each declared field for DBColumn annotation and get the name as key mapped to the field's object for each
        for (Field field: tableClass.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(DBColumn.class)) {
                String key = field.getAnnotation(DBColumn.class).name();
                Object value = field.get(dbObject);

                map.put(key, value);
            }
        }

        return map;
    }

}
