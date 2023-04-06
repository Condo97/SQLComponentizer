package sqlcomponentizer.preparedstatement.component.columns.aggregate;

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
