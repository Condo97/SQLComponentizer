package sqlcomponentizer.preparedstatement.component.condition;

public enum SQLOperators {
    EQUAL("="),
    GREATER_THAN(">"),
    LESS_THAN("<");

    private final String operatorString;

    SQLOperators(String operatorString) {
        this.operatorString = operatorString;
    }

    @Override
    public String toString() {
        return operatorString;
    }
}
