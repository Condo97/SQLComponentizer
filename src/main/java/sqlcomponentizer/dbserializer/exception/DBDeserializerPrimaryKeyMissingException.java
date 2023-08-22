package sqlcomponentizer.dbserializer.exception;

public class DBDeserializerPrimaryKeyMissingException extends Exception {

    public DBDeserializerPrimaryKeyMissingException(String message) {
        super(message);
    }

    public DBDeserializerPrimaryKeyMissingException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBDeserializerPrimaryKeyMissingException(Throwable cause) {
        super(cause);
    }

}
