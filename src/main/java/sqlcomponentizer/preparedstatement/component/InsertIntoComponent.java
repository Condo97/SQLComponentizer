package sqlcomponentizer.preparedstatement.component;

import sqlcomponentizer.preparedstatement.SQLTokens;

import java.util.*;

public class InsertIntoComponent implements PSPlaceholderComponent {

    private final String action = "INSERT INTO";
    private final String valuesWordAsText = "VALUES";
    private String tableName;
    private LinkedHashMap<String, Object> colValMap;

    public InsertIntoComponent(String tableName) {
        this.tableName = tableName;

        colValMap = new LinkedHashMap<>();
    }

    public InsertIntoComponent(String tableName, LinkedHashMap<String, Object> colValMap) {
        this.tableName = tableName;
        this.colValMap = colValMap;
    }

    public void insert(String columnName, Object value) {
        colValMap.put(columnName, value);
    }

    public void insert(Map<String, Object> colValMap) {
        this.colValMap.putAll(colValMap);
    }

    @Override
    public String getComponentString() {
        // INSERT INTO Table (col1, col2,...coln) VALUES (val1, val2,...valn)
        // Build the col and val lists based on key and value from keyValueMap
        StringBuilder colString = new StringBuilder();
        StringBuilder valPlaceholderString = new StringBuilder();
        colValMap.forEach((k, v) -> {
            colString.append(k + SQLTokens.COLUMN_SEPARATOR);
            valPlaceholderString.append(SQLTokens.PLACEHOLDER + SQLTokens.COLUMN_SEPARATOR);
        });

        // Delete last COLUMN_SEPARATOR from colString and valPlaceholderString
        colString.delete(colString.length() - SQLTokens.COLUMN_SEPARATOR.length(), colString.length());
        valPlaceholderString.delete(valPlaceholderString.length() - SQLTokens.COLUMN_SEPARATOR.length(), valPlaceholderString.length());

        return action + SQLTokens.SPACE + tableName + SQLTokens.SPACE + SQLTokens.OPEN_PARENTHESES + colString + SQLTokens.CLOSE_PARENTHESES + SQLTokens.SPACE + valuesWordAsText + SQLTokens.SPACE + SQLTokens.OPEN_PARENTHESES + valPlaceholderString + SQLTokens.CLOSE_PARENTHESES;
    }

    @Override
    public List<Object> getOrderedPlaceholderValues() {
        // Since the LinkedHashMap is ordered, just get the valuesList directly from it!
        List<Object> valuesList = new ArrayList<>();
        colValMap.forEach((k, v) -> valuesList.add(v));

        return valuesList;
    }
}
