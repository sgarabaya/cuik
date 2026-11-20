package cuik.data.models;

import java.util.List;
import java.util.UUID;

import cuik.data.Column;

public class Purchase extends BaseEntity {
    @Column("user_id")
    private UUID userId;

    private List<PurchaseItem> items;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public List<PurchaseItem> getItems() {
        return items;
    }

    public void setItems(List<PurchaseItem> items) {
        this.items = items;
    }
}
