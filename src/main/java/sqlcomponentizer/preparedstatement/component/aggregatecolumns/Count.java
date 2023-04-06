package sqlcomponentizer.preparedstatement.component.aggregatecolumns;

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
