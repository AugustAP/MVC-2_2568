package Model;

import java.time.LocalDate;

/**
 * Compensation Entity - ผลการคำนวณเงินเยียวยา
 */
public class Compensation {
    private String claimId;
    private double compensationAmount;
    private LocalDate calculationDate;

    public Compensation() {
        this.calculationDate = LocalDate.now();
    }

    public Compensation(String claimId, double compensationAmount) {
        this.claimId = claimId;
        this.compensationAmount = compensationAmount;
        this.calculationDate = LocalDate.now();
    }

    // Getters and Setters
    public String getClaimId() {
        return claimId;
    }

    public void setClaimId(String claimId) {
        this.claimId = claimId;
    }

    public double getCompensationAmount() {
        return compensationAmount;
    }

    public void setCompensationAmount(double compensationAmount) {
        this.compensationAmount = compensationAmount;
    }

    public LocalDate getCalculationDate() {
        return calculationDate;
    }

    public void setCalculationDate(LocalDate calculationDate) {
        this.calculationDate = calculationDate;
    }
}
