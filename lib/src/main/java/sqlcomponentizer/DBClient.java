package sqlcomponentizer;

import sqlcomponentizer.preparedstatement.PSBuildable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DBClient {

    /***
     * Gets a List<Map<String, Object>>, which feels like a lot, but really is just the ResultSet in a non-jdbc object.
     *
     * @param connection
     * @param psConnectable
     * @return
     * @throws SQLException
     */
    public static List<Map<String, Object>> query(Connection connection, PSBuildable psConnectable) throws SQLException {
        try (PreparedStatement ps = psConnectable.connect(connection)) {
            System.out.println(ps.toString());
            ResultSet rs = ps.executeQuery();
            return RSHelper.resultSetAsObjectMap(rs);
        } catch (SQLException e) {
            throw e;
        }
    }
}
