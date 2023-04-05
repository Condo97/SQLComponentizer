package sqlcomponentizer.preparedstatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PSConnectable {
    PreparedStatement connect(Connection connection) throws SQLException;
}
