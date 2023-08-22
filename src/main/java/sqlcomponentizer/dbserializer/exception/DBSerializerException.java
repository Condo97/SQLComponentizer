package sqlcomponentizer.dbserializer.exception;

public class DBSerializerException extends Exception {

    public DBSerializerException(String message) {
        super(message);
    }

    public DBSerializerException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBSerializerException(Throwable cause) {
        super(cause);
    }

}
