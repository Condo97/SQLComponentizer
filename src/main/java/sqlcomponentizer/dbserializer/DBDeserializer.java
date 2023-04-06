package sqlcomponentizer.dbserializer;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class DBDeserializer {

    public static void setPrimaryKey(Object dbObject, Object newPrimaryKey) throws DBSerializerException, IllegalAccessException, DBSerializerPrimaryKeyMissingException {
        // Check if DBSerializable
        DBSerializationValidator.checkSerializable(dbObject);

        // Loop through fields in dbObject until one where primaryKey is true is found, then set it to the object
        for (Field field: dbObject.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            if (field.isAnnotationPresent(DBColumn.class)) {
                if (field.getAnnotation(DBColumn.class).primaryKey()) {
                    field.set(dbObject, newPrimaryKey);
                    return;
                }
            }
        }

        throw new DBSerializerPrimaryKeyMissingException("Could not find primary key when attempting to setPrimaryKey in DBDeserializer");
    }

    public static Object getObjectFromMap(Class targetDBClass, Map<String, Object> tableMap) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, DBSerializerException {
        Object dbObject = targetDBClass.getConstructor().newInstance();

        fillObjectFromMap(dbObject, tableMap);

        return dbObject;
    }

    public static void fillObjectFromMap(Object dbObject, Map<String, Object> tableMap) throws DBSerializerException, IllegalAccessException {
        // Check if DBSerializable
        DBSerializationValidator.checkSerializable(dbObject);

        // Loop through fields in dbObject, loop through values in tableMap, if dbObject name equals tableMap key set the dbObject field to the corresponding value
        for (Field field: dbObject.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            // Only loop through tableMap keys and values if the DBColumn annotation is present to prove I'm still concerned about efficiency
            if (field.isAnnotationPresent(DBColumn.class)) {
                String fieldDBName = field.getAnnotation(DBColumn.class).name();

                for(String key: tableMap.keySet()) {
                    if (key.equals(fieldDBName)) {
                        // Get the object to minimize field.set() redundancy as there would be one in the if statement otherwise
                        Object objectToSet = tableMap.get(key);

                        // Try to convert Integer to Boolean if field is Boolean
                        if (field.getType() == Boolean.class && objectToSet instanceof Integer) {
                            // Convert integer to boolean if field is either 0 or 1, otherwise throw IllegalArgumentException
                            if ((Integer)objectToSet == 0)
                                objectToSet = false;
                            else
                                if ((Integer)objectToSet == 1)
                                    objectToSet = true;
                                else
                                    throw new IllegalArgumentException();
                        }

                        field.set(dbObject, objectToSet);
                        break;
                    }
                }
            }
        }
    }
}
