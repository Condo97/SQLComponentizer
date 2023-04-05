package sqlcomponentizer.preparedstatement.statement;

import sqlcomponentizer.preparedstatement.ComponentizedPreparedStatement;
import sqlcomponentizer.preparedstatement.ComponentizedPreparedStatementBuilder;
import sqlcomponentizer.preparedstatement.component.InsertIntoComponent;
import sqlcomponentizer.preparedstatement.component.PSComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InsertIntoComponentizedPreparedStatementBuilder implements ComponentizedPreparedStatementBuilder {

    private InsertIntoComponent insertInto;

    private InsertIntoComponentizedPreparedStatementBuilder(InsertIntoComponent insertInto) {
        this.insertInto = insertInto;
    }

    public static InsertIntoComponentizedPreparedStatementBuilder forTable(String tableName) {
        return new InsertIntoComponentizedPreparedStatementBuilder(new InsertIntoComponent(tableName));
    }

    /* Add col and val pairs */

    public InsertIntoComponentizedPreparedStatementBuilder addColAndVal(String column, Object value) {
        insertInto.insert(column, value);
        return this;
    }

    public InsertIntoComponentizedPreparedStatementBuilder addColAndVals(Map<String, Object> colValMap) {
        insertInto.insert(colValMap);
        return this;
    }

    public ComponentizedPreparedStatement build(boolean getGeneratedKeys) {
        List<PSComponent> orderedComponents = new ArrayList<>();
        orderedComponents.add(insertInto);

        return new ComponentizedPreparedStatement(orderedComponents, getGeneratedKeys);
    }

    @Override
    public ComponentizedPreparedStatement build() {
        return build(false);
    }
}
