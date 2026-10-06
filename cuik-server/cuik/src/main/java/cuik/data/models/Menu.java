package cuik.data.models;

import cuik.data.Column;

public class Menu extends BaseEntity {

    @Column
    public String name;

    @Column("is_active")
    public boolean isActive;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}
