package cuik.data.models;

import cuik.data.Column;

public class Employee extends User {
    @Column("is_admin")
    private boolean isAdmin;

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
