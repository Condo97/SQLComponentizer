package sqlcomponentizer.preparedstatement.component;

import sqlcomponentizer.preparedstatement.SQLTokens;

import java.util.ArrayList;
import java.util.List;

public class WhereComponent implements PSPlaceholderComponent {
    private final String action = "WHERE";
    private List<PSComponent> conditions;

    public WhereComponent() {
        this.conditions = new ArrayList<>();
    }

    public WhereComponent(List<PSComponent> conditions) {
        this.conditions = conditions;
    }

    public String getAction() {
        return action;
    }

    public void addCondition(PSComponent condition) {
        this.conditions.add(condition);
    }

    public void addConditions(List<PSComponent> conditions) {
        this.conditions.addAll(conditions);
    }

    @Override
    public String getComponentString() {
        // If no conditions, return a blank string
        if (conditions.size() == 0) return "";

        // col1=? AND col2=? AND
        // Build the component string
        StringBuilder sb = new StringBuilder();
        String andTokenWithSpaces = SQLTokens.SPACE + SQLTokens.AND + SQLTokens.SPACE;
        conditions.forEach(c -> sb.append(c.getComponentString() + andTokenWithSpaces));

        // Delete last AND added by conditions Consumer
        if (sb.length() >= andTokenWithSpaces.length())
            sb.delete(sb.length() - andTokenWithSpaces.length(), sb.length());



        // Return sb with action
        return action + SQLTokens.SPACE + sb;
    }

    @Override
    public List<Object> getOrderedPlaceholderValues() {
        List<Object> values = new ArrayList<>();

        conditions.forEach(c -> {
            if (c instanceof PSPlaceholderComponent)
                values.addAll(((PSPlaceholderComponent) c).getOrderedPlaceholderValues());
        });

        return values;
    }
}
