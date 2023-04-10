package sqlcomponentizer.preparedstatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PSConnectableWithGeneratedKeys extends PSConnectable {

    PreparedStatement connectGenerateKeys(Connection connection, boolean shouldGenerateKeys) throws SQLException;

}
