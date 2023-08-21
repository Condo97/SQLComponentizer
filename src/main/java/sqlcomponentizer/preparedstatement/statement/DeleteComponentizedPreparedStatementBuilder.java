package sqlcomponentizer.preparedstatement.statement;

import sqlcomponentizer.preparedstatement.ComponentizedPreparedStatement;
import sqlcomponentizer.preparedstatement.ComponentizedPreparedStatementBuilder;
import sqlcomponentizer.preparedstatement.DeleteComponent;
import sqlcomponentizer.preparedstatement.component.FromComponent;
import sqlcomponentizer.preparedstatement.component.PSComponent;
import sqlcomponentizer.preparedstatement.component.WhereComponent;
import sqlcomponentizer.preparedstatement.component.condition.SQLOperatorCondition;
import sqlcomponentizer.preparedstatement.component.condition.SQLOperators;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DeleteComponentizedPreparedStatementBuilder implements ComponentizedPreparedStatementBuilder {

    private DeleteComponent deleteComponent;
    private FromComponent fromComponent;
    private WhereComponent whereComponent;

    private DeleteComponentizedPreparedStatementBuilder(DeleteComponent deleteComponent, FromComponent fromComponent, WhereComponent whereComponent) {
        this.deleteComponent = deleteComponent;
        this.fromComponent = fromComponent;
        this.whereComponent = whereComponent;
    }

    public static DeleteComponentizedPreparedStatementBuilder forTable(String tableName) {
        return new DeleteComponentizedPreparedStatementBuilder(
                new DeleteComponent(),
                new FromComponent(tableName),
                new WhereComponent()
        );
    }

    /* WHERE */

    public DeleteComponentizedPreparedStatementBuilder where(String columnName, SQLOperators operator, Object value) {
        return where(Map.of(
                columnName, value
        ), operator);
    }

    public DeleteComponentizedPreparedStatementBuilder where(Map<String, Object> columnValueMap, SQLOperators commonOperator) {
        List<PSComponent> componentList = new ArrayList<>();
        columnValueMap.forEach((k,v) -> componentList.add(new SQLOperatorCondition(k, commonOperator, v)));

        return where(componentList);
    }

    public DeleteComponentizedPreparedStatementBuilder where(List<PSComponent> sqlConditions) {
        whereComponent.addConditions(sqlConditions);
        return this;
    }

    /* Build! */

    @Override
    public ComponentizedPreparedStatement build() {
        List<PSComponent> orderedComponents = new ArrayList<>();
        orderedComponents.add(deleteComponent);
        orderedComponents.add(fromComponent);
        orderedComponents.add(whereComponent);

        return new ComponentizedPreparedStatement(orderedComponents);
    }
}
