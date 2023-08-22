package sqlcomponentizer.dbserializer.exception;

public class DBDeserializerForeignKeyMissingException extends Exception {

    public DBDeserializerForeignKeyMissingException(String message) {
        super(message);
    }

    public DBDeserializerForeignKeyMissingException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBDeserializerForeignKeyMissingException(Throwable cause) {
        super(cause);
    }

}
