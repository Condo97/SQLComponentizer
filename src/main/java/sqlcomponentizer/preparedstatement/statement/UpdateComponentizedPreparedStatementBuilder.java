package sqlcomponentizer.preparedstatement.statement;

import sqlcomponentizer.preparedstatement.ComponentizedPreparedStatement;
import sqlcomponentizer.preparedstatement.ComponentizedPreparedStatementBuilder;
import sqlcomponentizer.preparedstatement.component.PSComponent;
import sqlcomponentizer.preparedstatement.component.SetComponent;
import sqlcomponentizer.preparedstatement.component.UpdateComponent;
import sqlcomponentizer.preparedstatement.component.WhereComponent;
import sqlcomponentizer.preparedstatement.component.condition.SQLNullCondition;
import sqlcomponentizer.preparedstatement.component.condition.SQLOperatorCondition;
import sqlcomponentizer.preparedstatement.component.condition.SQLOperators;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UpdateComponentizedPreparedStatementBuilder implements ComponentizedPreparedStatementBuilder {

    private UpdateComponent updateComponent;
    private SetComponent setComponent;
    private WhereComponent whereComponent;

    private UpdateComponentizedPreparedStatementBuilder(UpdateComponent updateComponent, SetComponent setComponent, WhereComponent whereComponent) {
        this.updateComponent = updateComponent;
        this.setComponent = setComponent;
        this.whereComponent = whereComponent;
    }

    public static UpdateComponentizedPreparedStatementBuilder forTable(String tableName) {
        return new UpdateComponentizedPreparedStatementBuilder(
                new UpdateComponent(tableName),
                new SetComponent(),
                new WhereComponent());
    }

    /* SET */

    public UpdateComponentizedPreparedStatementBuilder set(String column, Object newValue) {
        setComponent.addColVal(column, newValue);
        return this;
    }

    public UpdateComponentizedPreparedStatementBuilder set(Map<String, Object> colNewValueMap) {
        setComponent.addColVals(colNewValueMap);
        return this;
    }

    /* WHERE TODO: - These are repeated from SelectComponentizedPreparedStatementBuilder */


    public UpdateComponentizedPreparedStatementBuilder where(String columnName, SQLOperators operator, Object value) {
//        whereComponent.addCondition(new SQLOperatorCondition(columnName, operator, value)); TODO: Omg what is this why was this here
        return where(Map.of(
                columnName, value
        ), operator);
    }

    public UpdateComponentizedPreparedStatementBuilder where(Map<String, Object> columnValueMap, SQLOperators operatorForAll) {
        List<PSComponent> componentList = new ArrayList<>();
        columnValueMap.forEach((k,v) -> componentList.add(new SQLOperatorCondition(k, operatorForAll, v)));

        return where(componentList);
    }

    public UpdateComponentizedPreparedStatementBuilder where(List<PSComponent> sqlConditions) {
        whereComponent.addConditions(sqlConditions);
        return this;
    }

    private UpdateComponentizedPreparedStatementBuilder whereNullCondition(String columnName, boolean isNull) {
        whereComponent.addCondition(new SQLNullCondition(columnName, isNull));
        return this;
    }

    public UpdateComponentizedPreparedStatementBuilder whereNull(String columnName) {
        return whereNullCondition(columnName, true);
    }

    public UpdateComponentizedPreparedStatementBuilder whereNotNull(String columnName) {
        return whereNullCondition(columnName, false);
    }

    @Override
    public ComponentizedPreparedStatement build() {
        // UPDATE Table SET col1=val1 WHERE col10=val10
        List<PSComponent> orderedComponents = new ArrayList<>();
        orderedComponents.add(updateComponent);
        orderedComponents.add(setComponent);
        orderedComponents.add(whereComponent);

        return new ComponentizedPreparedStatement(orderedComponents);
    }
}
