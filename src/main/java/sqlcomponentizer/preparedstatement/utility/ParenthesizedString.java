package sqlcomponentizer.preparedstatement.utility;

import sqlcomponentizer.preparedstatement.SQLTokens;

public class ParenthesizedString {

    private String string;

    protected ParenthesizedString(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return SQLTokens.OPEN_PARENTHESES + string + SQLTokens.CLOSE_PARENTHESES;
    }
}
