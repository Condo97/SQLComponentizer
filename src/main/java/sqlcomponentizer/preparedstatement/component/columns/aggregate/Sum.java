package sqlcomponentizer.preparedstatement.component.columns.aggregate;

import sqlcomponentizer.preparedstatement.SQLTokens;

public class Sum {

    private final String action = "Sum";

    private String column;

    public Sum(String column) {
        this.column = column;
    }

    @Override
    public String toString() {
        return action + SQLTokens.OPEN_PARENTHESES + column + SQLTokens.CLOSE_PARENTHESES;
    }
}
