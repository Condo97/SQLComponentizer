package sqlcomponentizer.preparedstatement.component;

import sqlcomponentizer.preparedstatement.SQLTokens;

import java.util.ArrayList;
import java.util.List;

public class OrderByComponent implements PSComponent {

    private final String action = "ORDER BY";
    private List<String> columnList;

    public OrderByComponent() {
        columnList = new ArrayList<>();
    }

    public OrderByComponent(List<String> columnList) {
        this.columnList = columnList;
    }


    public void addColumn(String column) {
        columnList.add(column);
    }

    public void addColumns(List<String> columns) {
        columnList.addAll(columns);
    }

    @Override
    public String getComponentString() {
        // If no columns, return a blank string
        if (columnList.size() == 0) return "";

        // ORDER BY col1, col2,...coln
        StringBuilder sb = new StringBuilder();
        columnList.forEach(c -> sb.append(c + SQLTokens.COLUMN_SEPARATOR));

        // Delete trailing column separator
        sb.delete(sb.length() - SQLTokens.COLUMN_SEPARATOR.length(), sb.length());

        // Return component string
        return action + SQLTokens.SPACE + sb;
    }
}
