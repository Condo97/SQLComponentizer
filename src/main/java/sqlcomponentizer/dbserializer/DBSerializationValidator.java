package sqlcomponentizer.dbserializer;

import sqlcomponentizer.dbserializer.exception.DBSerializerException;

public class DBSerializationValidator {

    public static void checkSerializable(Object o) throws DBSerializerException {
        if (o == null) throw new DBSerializerException("Cannot serialize null object " + o);
        checkSerializable(o.getClass());
    }

    public static void checkSerializable(Class c) throws DBSerializerException {
        if (c == null) throw new DBSerializerException("Cannot serialize null class " + c);
        if (!c.isAnnotationPresent(DBSerializable.class)) throw new DBSerializerException("Cannot serialize object " + c);
    }

}
