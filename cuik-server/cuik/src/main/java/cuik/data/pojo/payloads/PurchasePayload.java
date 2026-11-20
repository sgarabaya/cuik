package cuik.data.pojo.payloads;

import java.util.List;
import java.util.UUID;

public record PurchasePayload(UUID userId, List<PurchaseItemPayload> items) {
}
