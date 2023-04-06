package sqlcomponentizer.preparedstatement.component;

import sqlcomponentizer.preparedstatement.SQLTokens;

import java.util.ArrayList;
import java.util.List;

public class SelectComponent implements PSComponent {
    private final String action = "SELECT";
    private List<String> columns;

    public SelectComponent() {
        columns = new ArrayList<>();
    }

    public SelectComponent(List<String> columns) {
        this.columns = columns;
    }

    public String getAction() {
        return action;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    @Override
    public String getComponentString() {
        // SELECT col1, col2,...coln\n

        // Set comma separated columns
        StringBuilder commaSeparatedColumns = new StringBuilder();
            // Add columns to commaSeparatedColumns
        if (columns.size() > 0) {
            columns.forEach(c -> commaSeparatedColumns.append(c + SQLTokens.COLUMN_SEPARATOR));

            // Delete last COMMA_SEPARATOR
            commaSeparatedColumns.delete(commaSeparatedColumns.length() - SQLTokens.COLUMN_SEPARATOR.length(), commaSeparatedColumns.length());
        } else {
            // If no columns specified, use the ALL_COLUMN_SYMBOL
            commaSeparatedColumns.append(SQLTokens.ALL);
        }

        return action + SQLTokens.SPACE + commaSeparatedColumns;
    }
}
