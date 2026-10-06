package cuik.data.models;

import cuik.data.Column;
import java.util.UUID;

public class MenuItemAllergen {

    @Column
    private UUID menuItemId;

    @Column
    private UUID allergenId;

    public UUID getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(UUID menuItemId) {
        this.menuItemId = menuItemId;
    }

    public UUID getAllergenId() {
        return allergenId;
    }

    public void setAllergenId(UUID allergenId) {
        this.allergenId = allergenId;
    }
}
