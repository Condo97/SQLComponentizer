package sqlcomponentizer.preparedstatement.component;

import sqlcomponentizer.preparedstatement.SQLTokens;

import java.util.ArrayList;
import java.util.List;

public class InnerJoinComponent implements PSComponent {

    private final String action = "INNER JOIN";
    private final String onKeyword = "ON";

    private String fromTable, joinTable;
    private List<String> columns;

    public InnerJoinComponent(String fromTable) {
        this.fromTable = fromTable;

        columns = new ArrayList<String>();
    }

    public InnerJoinComponent(String fromTable, String joinTable, List<String> columns) {
        this.fromTable = fromTable;
        this.joinTable = joinTable;
        this.columns = columns;
    }

    public void setJoinTable(String joinTable) {
        this.joinTable = joinTable;
    }

    public void addColumn(String column) {
        columns.add(column);
    }

    public void addColumns(List<String> columns) {
        this.columns.addAll(columns);
    }

    @Override
    public String getComponentString() {
        // INNER JOIN joinTable ON fromTable.Col1=joinTable.Col1 AND

        // If no columns, return a blank string
        if (joinTable == null || columns.size() == 0) return "";

        // Set commaSeparatedColumns
        StringBuilder commaSeparatedColumns = new StringBuilder();

        // Set SPACE AND SPACE for conciseness
        String andSeparator = SQLTokens.SPACE + SQLTokens.AND + SQLTokens.SPACE;

        // fromTable.column = joinTable.column AND
        columns.forEach(c -> commaSeparatedColumns.append(fromTable + SQLTokens.DOT + c + SQLTokens.EQUAL_SYMBOL + joinTable + SQLTokens.DOT + c + andSeparator));

        // Delete last SPACE AND SPACE
        commaSeparatedColumns.delete(commaSeparatedColumns.length() - andSeparator.length(), commaSeparatedColumns.length());

        // Return finished component string
        return action + SQLTokens.SPACE + joinTable + SQLTokens.SPACE + onKeyword + SQLTokens.SPACE + commaSeparatedColumns;
    }

}
