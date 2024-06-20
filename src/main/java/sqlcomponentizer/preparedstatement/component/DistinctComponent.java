package sqlcomponentizer.preparedstatement.component;

public class DistinctComponent implements PSComponent {

    private final String action = "DISTINCT";

    public DistinctComponent() {

    }

    public String getAction() {
        return action;
    }

    @Override
    public String getComponentString() {
        return action;
    }

}
