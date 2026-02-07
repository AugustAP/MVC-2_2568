package Model;

import java.time.LocalDate;

/**
 * Claim Model - คำขอเยียวยาทั่วไป
 * สำหรับผู้มีรายได้ >= 6500 และ <= 50000 บาทต่อเดือน
 */
public class Claim {
    protected String claimId;
    protected String claimantId;
    protected LocalDate submissionDate;
    protected String status;
    protected double monthlyIncome;
    
    public Claim() {
        this.status = "รอดำเนินการ";
        this.submissionDate = LocalDate.now();
    }
    
    public Claim(String claimId, String claimantId, double monthlyIncome) {
        this.claimId = claimId;
        this.claimantId = claimantId;
        this.monthlyIncome = monthlyIncome;
        this.submissionDate = LocalDate.now();
        this.status = "รอดำเนินการ";
    }
    
    /**
     * คำนวณเงินเยียวยาสำหรับผู้มีรายได้ทั่วไป
     * รายได้ >= 6500 และ <= 50000 บาท
     * ได้เงินเยียวยาตามรายได้ต่อเดือน แต่ไม่เกิน 20000 บาท
     */
    public double calculateCompensation() {
        if (monthlyIncome >= 6500 && monthlyIncome <= 50000) {
            return Math.min(monthlyIncome, 20000);
        }
        return 0;
    }
    
    // Getters and Setters
    public String getClaimId() {
        return claimId;
    }
    
    public void setClaimId(String claimId) {
        this.claimId = claimId;
    }
    
    public String getClaimantId() {
        return claimantId;
    }
    
    public void setClaimantId(String claimantId) {
        this.claimantId = claimantId;
    }
    
    public LocalDate getSubmissionDate() {
        return submissionDate;
    }
    
    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public double getMonthlyIncome() {
        return monthlyIncome;
    }
    
    public void setMonthlyIncome(double monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }
    
    public String getClaimType() {
        return "ทั่วไป";
    }
}
