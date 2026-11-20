package cuik.data.models;

import java.util.UUID;

import cuik.data.Column;

public class PurchaseItem extends BaseEntity {
    @Column("purchase_id")
    private UUID purchaseId;

    @Column("menu_item_id")
    private UUID menuItemId;

    @Column
    private double price;

    @Column
    private int units;

    public UUID getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(UUID purchaseId) {
        this.purchaseId = purchaseId;
    }

    public UUID getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(UUID menuItemId) {
        this.menuItemId = menuItemId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }
}
