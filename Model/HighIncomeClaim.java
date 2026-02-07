package Model;

/**
 * HighIncomeClaim Model - คำขอเยียวยาสำหรับผู้มีรายได้สูง
 * สำหรับผู้มีรายได้ >= 50000 บาทต่อเดือน
 */
public class HighIncomeClaim extends Claim {
    
    public HighIncomeClaim() {
        super();
    }
    
    public HighIncomeClaim(String claimId, String claimantId, double monthlyIncome) {
        super(claimId, claimantId, monthlyIncome);
    }
    
    /**
     * คำนวณเงินเยียวยาสำหรับผู้มีรายได้สูง
     * รายได้ >= 50000 บาท
     * ได้เงินเยียวยาตามรายได้ต่อเดือนหารด้วย 5 แต่ไม่เกิน 20000 บาท
     */
    @Override
    public double calculateCompensation() {
        if (monthlyIncome >= 50000) {
            return Math.min(monthlyIncome / 5, 20000);
        }
        return 0;
    }
    
    @Override
    public String getClaimType() {
        return "รายได้สูง";
    }
}
