package sqlcomponentizer.preparedstatement.component.condition;

import sqlcomponentizer.preparedstatement.SQLTokens;
import sqlcomponentizer.preparedstatement.component.PSPlaceholderComponent;

import java.util.List;

public class SQLOperatorCondition implements PSPlaceholderComponent {
    String column;
    SQLOperators operator;
    Object value;

    public SQLOperatorCondition() {

    }

    public SQLOperatorCondition(String column, SQLOperators operator, Object value) {
        this.column = column;
        this.operator = operator;
        this.value = value;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public SQLOperators getOperator() {
        return operator;
    }

    public void setOperator(SQLOperators operator) {
        this.operator = operator;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String getComponentString() {
        return column + operator + SQLTokens.PLACEHOLDER;
    }

    @Override
    public List<Object> getOrderedPlaceholderValues() {
        return List.of(value);
    }
}
