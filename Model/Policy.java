package Model;

/**
 * Policy Entity - นโยบายการเยียวยา
 */
public class Policy {
    private String policyId;
    private double maxCompensation;
    private String incomeCondition;

    public Policy() {
    }

    public Policy(String policyId, double maxCompensation, String incomeCondition) {
        this.policyId = policyId;
        this.maxCompensation = maxCompensation;
        this.incomeCondition = incomeCondition;
    }

    // Getters and Setters
    public String getPolicyId() {
        return policyId;
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }

    public double getMaxCompensation() {
        return maxCompensation;
    }

    public void setMaxCompensation(double maxCompensation) {
        this.maxCompensation = maxCompensation;
    }

    public String getIncomeCondition() {
        return incomeCondition;
    }

    public void setIncomeCondition(String incomeCondition) {
        this.incomeCondition = incomeCondition;
    }
}
