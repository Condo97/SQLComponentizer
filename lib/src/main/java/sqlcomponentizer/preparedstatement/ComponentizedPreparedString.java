package sqlcomponentizer.preparedstatement;

import sqlcomponentizer.preparedstatement.component.PSComponent;
import sqlcomponentizer.preparedstatement.component.PSPlaceholderComponent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComponentizedPreparedString implements PSBuildable {
    // Common variables
    Connection conn;
    List<PSComponent> components;

    public ComponentizedPreparedString(Connection conn, List<PSComponent> components) {
        this.conn = conn;
        this.components = components;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public List<PSComponent> getComponents() {
        return components;
    }

    public void setComponents(List<PSComponent> components) {
        this.components = components;
    }

    private String getPreparedStatementString() {
        StringBuilder sb = new StringBuilder();
        components.forEach(c -> {
            // Make it pretty by not adding extra spaces :)
            String componentString = c.getComponentString();
            if (componentString != null && !componentString.equals(""))
                sb.append(componentString + SQLTokens.SPACE);
        });

        // Remove tail SPACE if there is enough space for it to exist
        if (sb.length() >= SQLTokens.SPACE.length())
            sb.delete(sb.length() - SQLTokens.SPACE.length(), sb.length());

        // Return prepared statement string with terminator
        return sb + SQLTokens.TERMINATOR;
    }
    private List<Object> getOrderedPlaceholderValues() {
        List<Object> values = new ArrayList<>();
        components.forEach(c -> {
            if (c instanceof PSPlaceholderComponent)
                values.addAll(((PSPlaceholderComponent) c).getOrderedPlaceholderValues());
        });

        return values;
    }

    @Override
    public PreparedStatement connect(Connection connection) throws SQLException {
        // Setup PS with prepared statement string
        PreparedStatement ps = connection.prepareStatement(getPreparedStatementString());

        // Get ordered placeholders
        List<Object> orderedPlaceholders = getOrderedPlaceholderValues();

        // Fill the PS with the objects, at incrementing indices
        for (int i = 0; i < orderedPlaceholders.size(); i++) {
            ps.setObject(i + 1, orderedPlaceholders.get(i));
        }

        // Return the PS!
        return ps;
    }
}
