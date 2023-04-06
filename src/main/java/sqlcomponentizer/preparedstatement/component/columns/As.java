package sqlcomponentizer.preparedstatement.component.columns;

import sqlcomponentizer.preparedstatement.SQLTokens;

public class As {

    private final String action = "AS";
    private String column, alias;

    public As(String column, String alias) {
        this.column = column;
        this.alias = alias;
    }

    @Override
    public String toString() {
        return column + SQLTokens.SPACE + action + SQLTokens.SPACE + alias;
    }

}
