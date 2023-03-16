package sqlcomponentizer.dbserializer;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class DBSerializer {

    public static String getTableName(Object dbObject) throws DBSerializerException {
        // Check if DBSerializable and get tableName
        DBSerializationValidator.checkSerializable(dbObject);

        return dbObject.getClass().getAnnotation(DBSerializable.class).tableName();
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
