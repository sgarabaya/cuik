package cuik.data.models;

import java.util.List;

import cuik.data.Column;

public class Menu extends BaseEntity {
    @Column
    private String name;

    @Column("is_active")
    private boolean isActive;

    private List<MenuItem> items;

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

    public List<MenuItem> getItems() {
        return items;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }
}
