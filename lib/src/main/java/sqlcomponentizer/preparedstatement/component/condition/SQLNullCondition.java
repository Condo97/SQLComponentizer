package sqlcomponentizer.preparedstatement.component.condition;

import sqlcomponentizer.preparedstatement.component.PSComponent;

public class SQLNullCondition implements PSComponent {
    private final String IS_NULL = "IS NULL";
    private final String IS_NOT_NULL = "IS NOT NULL";

    private String column;
    private boolean isNull;

    public SQLNullCondition() {

    }

    public SQLNullCondition(String column, boolean isNull) {
        this.column = column;
        this.isNull = isNull;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public boolean getIsNull() {
        return isNull;
    }

    public void setIsNull(boolean isNull) {
        this.isNull = isNull;
    }

    @Override
    public String getComponentString() {
        return column + " " + (isNull ? IS_NULL : IS_NOT_NULL);
    }
}
