package sqlcomponentizer.preparedstatement.component.columns.aggregate;

import sqlcomponentizer.preparedstatement.utility.ParenthesizedString;

public class Count extends ParenthesizedString {

    private final String action = "COUNT";
    private final String distinctString = "DISTINCT";

    private Boolean distinct;

    public Count(String column, Boolean distinct) {
        super(column);
        this.distinct = distinct;
    }

    @Override
    public String toString() {
        // TODO: This is less concise than it was previously, is there a way to make it more concise?
        if (distinct) {
            return distinctString + action + super.toString();
        } else {
            return action + super.toString();
        }
    }
}
