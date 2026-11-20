package cuik.data.pojo.payloads;

import java.util.UUID;

public record PurchaseItemPayload(UUID menuItemId, Double price, Integer units) {
}
