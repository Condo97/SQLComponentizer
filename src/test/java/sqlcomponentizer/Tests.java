package sqlcomponentizer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sqlcomponentizer.preparedstatement.ComponentizedPreparedStatement;
import sqlcomponentizer.preparedstatement.component.condition.SQLOperators;
import sqlcomponentizer.preparedstatement.statement.DeleteComponentizedPreparedStatementBuilder;
import sqlcomponentizer.preparedstatement.statement.InsertIntoComponentizedPreparedStatementBuilder;
import sqlcomponentizer.preparedstatement.statement.SelectComponentizedPreparedStatementBuilder;
import sqlcomponentizer.preparedstatement.statement.UpdateComponentizedPreparedStatementBuilder;

import java.sql.*;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class Tests {

    @Test
    @DisplayName("Testing SelectComponentizedPreparedStatementBuilder")
    public void testSelectSimplePreparedStatementBuilder() throws SQLException {
        ComponentizedPreparedStatement cps = SelectComponentizedPreparedStatementBuilder
                .forTable("User_AuthToken")
                .select("user_id")
                .innerJoin("Receipt", "user_id")
                .where("auth_token", SQLOperators.EQUAL, "EeOlvXHVIky6pUZRZpNcJlNr8aKjit88JKUSS6y2pqjZDrPOnSFTQzZT09UbM7s/wX4t/rmb05fm+iPCC6tVyRqWEWn5eyGMBpJKdRpi6YfJ3lTbFjRTgcki5QhjwvMpsADx2tjOYaWGyUU+74DV9gx/CAqoayUPvEpqhGsVtY0=")
                .build();

        System.out.println(cps.toString());

    }

    @Test
    @DisplayName("Testing InsertComponentizedPreparedStatementBuilder")
    public void testInsertComponentizedPreparedStatementBuilder() throws SQLException, IllegalAccessException {
        ComponentizedPreparedStatement cps = InsertIntoComponentizedPreparedStatementBuilder.forTable("Chat").addColAndVal("chat_id", 1234).addColAndVal("user_id", 4321).addColAndVals(new LinkedHashMap<>(Map.of("user_text", "adsfasdfasdf", "date", new Timestamp(new Date().getTime())))).build();

        System.out.println(cps);

    }

    @Test
    @DisplayName("Testing UpdateComponentizedPreparedStatementBuilder")
    public void testUpdateComponentizedPreparedStatementBuilder() throws SQLException, IllegalAccessException {
        ComponentizedPreparedStatement cps = UpdateComponentizedPreparedStatementBuilder.forTable("Chat").set("ai_text", "hello there").where("user_id", SQLOperators.EQUAL, 5).build();

        System.out.println(cps);

    }

    @Test
    @DisplayName("Testing DeleteComponentizedPreparedStatementBuilder")
    public void testDeleteComponentizedPreparedStatementBuilder() {
        ComponentizedPreparedStatement cps = DeleteComponentizedPreparedStatementBuilder
                .forTable("Chat")
                .where("chat_id", SQLOperators.EQUAL, 5)
                .build();

        System.out.println(cps);
    }

}
