package sqlcomponentizer.preparedstatement;

import sqlcomponentizer.preparedstatement.component.PSComponent;

import java.util.List;

public class DeleteComponent implements PSComponent {

    private final String action = "DELETE";

    public DeleteComponent() {

    }

    public String getAction() {
        return action;
    }

    @Override
    public String getComponentString() {
        // DELETE

        return action;
    }
}
