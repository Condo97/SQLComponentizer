package sqlcomponentizer.preparedstatement.component.columns.aggregate;

import sqlcomponentizer.preparedstatement.utility.ParenthesizedString;

public class Count extends ParenthesizedString {

    private final String action = "COUNT";

    public Count(String column) {
        super(column);
    }

    @Override
    public String toString() {
        return action + super.toString();
    }
}
