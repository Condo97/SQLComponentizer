package sqlcomponentizer.preparedstatement.component;

import sqlcomponentizer.preparedstatement.SQLTokens;

public class FromComponent implements PSComponent {

    private final String action = "FROM";
    private String table;

    public FromComponent(String table) {
        this.table = table;
    }

    public String getAction() {
        return action;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    @Override
    public String getComponentString() {
        return action + SQLTokens.SPACE + table;
    }
}
