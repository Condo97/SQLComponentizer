package sqlcomponentizer.preparedstatement;

import sqlcomponentizer.preparedstatement.component.PSComponent;
import sqlcomponentizer.preparedstatement.component.PSPlaceholderComponent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ComponentizedPreparedStatement implements PSConnectable {
    // Common variables
    List<PSComponent> components;
    Boolean getGeneratedKeys;

    public ComponentizedPreparedStatement(List<PSComponent> components) {
        this.components = components;

        getGeneratedKeys = false;
    }

    public ComponentizedPreparedStatement(List<PSComponent> components, Boolean getGeneratedKeys) {
        this.components = components;
        this.getGeneratedKeys = getGeneratedKeys;
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
        PreparedStatement ps = connection.prepareStatement(getPreparedStatementString(), getGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);

        // Get ordered placeholders
        List<Object> orderedPlaceholders = getOrderedPlaceholderValues();

        // Fill the PS with the objects, at incrementing indices
        for (int i = 0; i < orderedPlaceholders.size(); i++) {
            ps.setObject(i + 1, orderedPlaceholders.get(i));
        }

        // Return the PS!
        return ps;
    }

    @Override
    public String toString() {
        return getPreparedStatementString() + " with placeholders " + getOrderedPlaceholderValues();
    }
}
