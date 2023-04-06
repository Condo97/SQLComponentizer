package sqlcomponentizer.preparedstatement.component.aggregatecolumns;

import sqlcomponentizer.preparedstatement.utility.ParenthesizedString;

public class Avg extends ParenthesizedString {

    private final String action = "AVG";

    public Avg(String column) {
        super(column);
    }

    @Override
    public String toString() {
        return action + super.toString();
    }
}
