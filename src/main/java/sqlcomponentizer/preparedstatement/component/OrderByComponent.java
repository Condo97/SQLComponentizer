package sqlcomponentizer.preparedstatement.component;

import sqlcomponentizer.preparedstatement.SQLTokens;

import java.util.ArrayList;
import java.util.List;

public class OrderByComponent implements PSComponent {

    public enum Direction {
        ASC(true),
        DESC(false);

        private Boolean isAscending;

        Direction(Boolean isAscending) {
            this.isAscending = isAscending;
        }

        public Boolean getIsAscending() {
            return isAscending;
        }
    }

    private final String action = "ORDER BY";

    private final String ascendingText = "ASC";
    private final String descendingText = "DESC";
    private boolean ascending = false;
    private List<String> columnList;

    public OrderByComponent() {
        columnList = new ArrayList<>();
    }

    public OrderByComponent(List<String> columnList) {
        this.columnList = columnList;
    }

    public void addColumn(Direction direction, String column) {
        addColumns(direction, List.of(column));
    }

    public void addColumns(Direction direction, List<String> columns) {
        // Null check, if null don't modify ascending, if not null then update with value from direction
        if (direction != null) ascending = direction.getIsAscending();
        if (columns != null) columnList.addAll(columns);
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
        return action + SQLTokens.SPACE + sb + SQLTokens.SPACE + (ascending ? ascendingText : descendingText);
    }
}
