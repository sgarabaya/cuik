package cuik.data.models;

import cuik.data.Column;

public class Employee extends BaseEntity {

    @Column("is_admin")
    public boolean isAdmin;

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
