package sqlcomponentizer.dbserializer;

public class DBSerializationValidator {

    public static void checkSerializable(Object o) throws DBSerializerException {
        if (o == null) throw new DBSerializerException("Cannot serialize null object " + o.getClass());
        if (!o.getClass().isAnnotationPresent(DBSerializable.class)) throw new DBSerializerException("Cannot serialize object " + o.getClass());
    }

}
