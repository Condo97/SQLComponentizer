package sqlcomponentizer.preparedstatement.statement;

import sqlcomponentizer.preparedstatement.ComponentizedPreparedStatement;
import sqlcomponentizer.preparedstatement.ComponentizedPreparedStatementBuilder;
import sqlcomponentizer.preparedstatement.component.*;
import sqlcomponentizer.preparedstatement.component.condition.SQLNullCondition;
import sqlcomponentizer.preparedstatement.component.condition.SQLOperatorCondition;
import sqlcomponentizer.preparedstatement.component.condition.SQLOperators;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class  SelectComponentizedPreparedStatementBuilder implements ComponentizedPreparedStatementBuilder {
    // This class basically assures that the components are in order for the SimplePreparedStatement and acts as a builder

    private SelectComponent selectComponent;
    private FromComponent fromComponent;
    private InnerJoinComponent innerJoinComponent;
    private WhereComponent whereComponent;
    private OrderByComponent orderByComponent;
    private LimitComponent limitComponent;

    private SelectComponentizedPreparedStatementBuilder(SelectComponent selectComponent, FromComponent fromComponent, InnerJoinComponent innerJoinComponent, WhereComponent whereComponent, OrderByComponent orderByComponent, LimitComponent limitComponent) {
        this.selectComponent = selectComponent;
        this.fromComponent = fromComponent;
        this.innerJoinComponent = innerJoinComponent;
        this.whereComponent = whereComponent;
        this.orderByComponent = orderByComponent;
        this.limitComponent = limitComponent;
    }

    public static SelectComponentizedPreparedStatementBuilder forTable(String tableName) {
        return new SelectComponentizedPreparedStatementBuilder(
                new SelectComponent(),
                new FromComponent(tableName),
                new InnerJoinComponent(tableName),
                new WhereComponent(),
                new OrderByComponent(),
                new LimitComponent());
    }

    /* SELECT */

    public SelectComponentizedPreparedStatementBuilder select(String... columnNames) {
        select(List.of(columnNames));
        return this;
    }

    public SelectComponentizedPreparedStatementBuilder select(List<String> columnNames) {
        selectComponent.getColumns().addAll(columnNames);
        return this;
    }

    /* INNER JOIN */

    public SelectComponentizedPreparedStatementBuilder innerJoin(String joinTable, String... columnNames) {
        return innerJoin(joinTable, List.of(columnNames));
    }

    public SelectComponentizedPreparedStatementBuilder innerJoin(String joinTable, List<String> columnNames) {
        innerJoinComponent.setJoinTable(joinTable);
        innerJoinComponent.addColumns(columnNames);
        return this;
    }

    /* WHERE */

    public SelectComponentizedPreparedStatementBuilder where(String columnName, SQLOperators operator, Object value) {
        whereComponent.addCondition(new SQLOperatorCondition(columnName, operator, value));
        return where(Map.of(
                columnName, value
        ), operator);
    }

    public SelectComponentizedPreparedStatementBuilder where(Map<String, Object> columnValueMap, SQLOperators operatorForAll) {
        List<PSComponent> componentList = new ArrayList<>();
        columnValueMap.forEach((k,v) -> componentList.add(new SQLOperatorCondition(k, operatorForAll, v)));

        return where(componentList);
    }

    public SelectComponentizedPreparedStatementBuilder where(List<PSComponent> sqlConditions) {
        whereComponent.addConditions(sqlConditions);
        return this;
    }

    private SelectComponentizedPreparedStatementBuilder whereNullCondition(String columnName, boolean isNull) {
        whereComponent.addCondition(new SQLNullCondition(columnName, isNull));
        return this;
    }

    public SelectComponentizedPreparedStatementBuilder whereNull(String columnName) {
        return whereNullCondition(columnName, true);
    }

    public SelectComponentizedPreparedStatementBuilder whereNotNull(String columnName) {
        return whereNullCondition(columnName, false);
    }

    /* ORDER BY */

    public SelectComponentizedPreparedStatementBuilder orderBy(OrderByComponent.Direction direction, String... columnNames) {
        orderBy(direction, List.of(columnNames));
        return this;
    }

    public SelectComponentizedPreparedStatementBuilder orderBy(OrderByComponent.Direction direction, List<String> columnNames) {
        orderByComponent.addColumns(direction, columnNames);
        return this;
    }

    /* LIMIT */

    public SelectComponentizedPreparedStatementBuilder limit(Integer limit) {
        limitComponent.setAmount(limit);
        return this;
    }

    /* Build! */

    @Override
    public ComponentizedPreparedStatement build() {
        List<PSComponent> orderedComponents = new ArrayList<>();
        orderedComponents.add(selectComponent);
        orderedComponents.add(fromComponent);
        orderedComponents.add(innerJoinComponent);
        orderedComponents.add(whereComponent);
        orderedComponents.add(orderByComponent);
        orderedComponents.add(limitComponent);

        return new ComponentizedPreparedStatement(orderedComponents);
    }
}
