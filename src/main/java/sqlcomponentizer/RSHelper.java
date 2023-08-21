//package sqlcomponentizer;
//
//import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class RSHelper {
//    public static List<Map<String, Object>> resultSetAsObjectMap(ResultSet rs) throws SQLException {
//        ArrayList<Map<String, Object>> list = new ArrayList();
//        ResultSetMetaData md = rs.getMetaData();
//
//        while (rs.next()) {
//            Map<String, Object> m = new HashMap<>();
//            for (int i = 0; i < md.getColumnCount(); i++) {
//                m.put(md.getColumnLabel(i + 1), rs.getObject(i + 1));
//            }
//            list.add(m);
//        }
//
//        return list;
//    }
//}
