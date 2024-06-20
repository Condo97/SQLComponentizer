package sqlcomponentizer.preparedstatement.component.columns.aggregate;

import sqlcomponentizer.preparedstatement.SQLTokens;

public class Count {

    private final String action = "COUNT";
    private final String distinctString = "DISTINCT";

    private String column;
    private Boolean distinct;

    public Count(String column) {
        this.column = column;

        distinct = false;
    }

    public Count(String column, Boolean distinct) {
        this.column = column;
        this.distinct = distinct;
    }

    @Override
    public String toString() {
        return action + SQLTokens.SPACE + SQLTokens.OPEN_PARENTHESES + (distinct ? distinctString + SQLTokens.SPACE : "") + column + SQLTokens.CLOSE_PARENTHESES;
    }

}
