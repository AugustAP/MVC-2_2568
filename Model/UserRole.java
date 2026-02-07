package Model;

/**
 * UserRole - ประเภทของผู้ใช้งานในระบบ
 */
public enum UserRole {
    OFFICER("เจ้าหน้าที่"),
    CITIZEN("ประชาชน");

    private final String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
