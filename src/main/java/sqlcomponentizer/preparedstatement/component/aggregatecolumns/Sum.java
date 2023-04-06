package sqlcomponentizer.preparedstatement.component.aggregatecolumns;

import sqlcomponentizer.preparedstatement.utility.ParenthesizedString;

public class Sum extends ParenthesizedString {

    private final String action = "Sum";

    public Sum(String column) {
        super(column);
    }

    @Override
    public String toString() {
        return action + super.toString();
    }
}
