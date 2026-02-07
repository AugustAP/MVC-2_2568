package Model;

/**
 * LowIncomeClaim Model - คำขอเยียวยาสำหรับผู้มีรายได้น้อย
 * สำหรับผู้มีรายได้ < 6500 บาทต่อเดือน
 */
public class LowIncomeClaim extends Claim {
    
    public LowIncomeClaim() {
        super();
    }
    
    public LowIncomeClaim(String claimId, String claimantId, double monthlyIncome) {
        super(claimId, claimantId, monthlyIncome);
    }
    
    /**
     * คำนวณเงินเยียวยาสำหรับผู้มีรายได้น้อย
     * รายได้ < 6500 บาท
     * ได้เงินเยียวยา 6500 บาท
     */
    @Override
    public double calculateCompensation() {
        if (monthlyIncome < 6500) {
            return 6500;
        }
        return 0;
    }
    
    @Override
    public String getClaimType() {
        return "รายได้น้อย";
    }
}
