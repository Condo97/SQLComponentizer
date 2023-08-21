//package sqlcomponentizer;
//
//import sqlcomponentizer.preparedstatement.PSConnectable;
//import sqlcomponentizer.preparedstatement.PSConnectableWithGeneratedKeys;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.List;
//import java.util.Map;
//
//public class DBClient {
//
//    /***
//     * Gets a List<Map<String, Object>>, which feels like a lot, but really is just the ResultSet in a non-jdbc object.
//     *
//     * @param connection
//     * @param psConnectable
//     * @return
//     * @throws SQLException
//     */
//    public static List<Map<String, Object>> query(Connection connection, PSConnectable psConnectable) throws SQLException {
//        try (PreparedStatement ps = psConnectable.connect(connection)) {
//            ResultSet rs = ps.executeQuery();
//            return RSHelper.resultSetAsObjectMap(rs);
//        } catch (SQLException e) {
//            throw e;
//        }
//    }
//
//    public static List<Map<String, Object>> updateReturnGeneratedKeys(Connection connection, PSConnectableWithGeneratedKeys psConnectable) throws SQLException {
//        try (PreparedStatement ps = psConnectable.connectGenerateKeys(connection, true)) {
//            ps.executeUpdate();
//
//            ResultSet rs = ps.getGeneratedKeys();
//            return RSHelper.resultSetAsObjectMap(rs);
//        } catch (SQLException e) {
//            throw e;
//        }
//    }
//
//    public static void update(Connection connection, PSConnectable psConnectable) throws SQLException {
//        try (PreparedStatement ps = psConnectable.connect(connection)) {
//            ps.executeUpdate();
//        } catch (SQLException e) {
//            throw e;
//        }
//    }
//}
