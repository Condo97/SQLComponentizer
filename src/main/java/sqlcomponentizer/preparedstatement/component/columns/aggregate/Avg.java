package sqlcomponentizer.preparedstatement.component.columns.aggregate;

import sqlcomponentizer.preparedstatement.SQLTokens;

public class Avg {

    private final String action = "AVG";

    private String column;

    public Avg(String column) {
        this.column = column;
    }

    @Override
    public String toString() {
        return action + SQLTokens.OPEN_PARENTHESES + column + SQLTokens.CLOSE_PARENTHESES;
    }
}
