package sqlcomponentizer.preparedstatement.component;

import sqlcomponentizer.preparedstatement.SQLTokens;

public class LimitComponent implements PSComponent {

    private final String action = "LIMIT";
    private Integer amount;

    public LimitComponent() {
        amount = null;
    }

    public LimitComponent(Integer amount) {
        this.amount = amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String getComponentString() {
        // Return empty string if amount is null or 0 or below
        if (amount == null || amount <= 0) return "";

        return action + SQLTokens.SPACE + amount;
    }
}
