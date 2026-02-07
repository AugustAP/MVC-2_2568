package Model;

/**
 * Claimant Entity - ผู้ขอเยียวยา
 */
public class Claimant {
    private String claimantId;
    private String firstName;
    private String lastName;
    private double monthlyIncome;
    private String claimantType; // "ทั่วไป", "รายได้น้อย", "รายได้สูง"

    public Claimant() {
    }

    private String username;
    private String password;

    public Claimant(String claimantId, String firstName, String lastName, double monthlyIncome, String claimantType,
            String username, String password) {
        this.claimantId = claimantId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.monthlyIncome = monthlyIncome;
        this.claimantType = claimantType;
        this.username = username;
        this.password = password;
    }

    // Getters and Setters
    public String getClaimantId() {
        return claimantId;
    }

    public void setClaimantId(String claimantId) {
        this.claimantId = claimantId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(double monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public String getClaimantType() {
        return claimantType;
    }

    public void setClaimantType(String claimantType) {
        this.claimantType = claimantType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
