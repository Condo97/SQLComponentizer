package sqlcomponentizer.preparedstatement.component;

import sqlcomponentizer.preparedstatement.SQLTokens;
import sqlcomponentizer.preparedstatement.component.PSComponent;

public class UpdateComponent implements PSComponent {
    private final String action = "UPDATE";
    private String tableName;

    public UpdateComponent(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String getComponentString() {
        return action + SQLTokens.SPACE + tableName;
    }
}
