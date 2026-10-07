package cuik.data.models;

import cuik.data.Column;
import java.util.UUID;

public class MenuItem extends BaseEntity {

    @Column("menu_id")
    private UUID menuId;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private float price;

    @Column
    private boolean available;

    @Column("estimated_prep_time")
    private int estimatedPrepTime;

    public UUID getMenuId() {
        return menuId;
    }

    public void setMenuId(UUID menuId) {
        this.menuId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getEstimatedPrepTime() {
        return estimatedPrepTime;
    }

    public void setEstimatedPrepTime(int estimatedPrepTime) {
        this.estimatedPrepTime = estimatedPrepTime;
    }
}
