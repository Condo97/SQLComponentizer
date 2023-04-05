package sqlcomponentizer.preparedstatement.component;

import sqlcomponentizer.preparedstatement.SQLTokens;
import sqlcomponentizer.preparedstatement.component.condition.SQLOperators;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SetComponent implements PSPlaceholderComponent {
    private final String action = "SET";
    private LinkedHashMap<String, Object> colValMap;

    public SetComponent() {
        colValMap = new LinkedHashMap<>();
    }

    public void addColVal(String col, Object val) {
        colValMap.put(col, val);
    }

    public void addColVals(Map<String, Object> colValMap) {
        this.colValMap.putAll(colValMap);
    }

    @Override
    public String getComponentString() {
        // Build col and val string, "col=val, "
        StringBuilder sb = new StringBuilder();
        colValMap.forEach((k, v) -> sb.append(k + SQLTokens.EQUAL_SYMBOL + SQLTokens.PLACEHOLDER + SQLTokens.COLUMN_SEPARATOR)); // TODO: - Test if colValMap.getKeySet() can be iterated through with order kept, since value is not used here

        // Remove the trailing COLUMN_SEPARATOR
        sb.delete(sb.length() - SQLTokens.COLUMN_SEPARATOR.length(), sb.length());

        // Return string with placeholders
        return action + SQLTokens.SPACE + sb;
    }

    @Override
    public List<Object> getOrderedPlaceholderValues() {
        // Create an arrayList of the placeholder values in order
        List<Object> placeholderValues = new ArrayList<>();
        colValMap.forEach((k, v) -> placeholderValues.add(v));

        return placeholderValues;
    }
}
