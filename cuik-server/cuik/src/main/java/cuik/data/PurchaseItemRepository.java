package cuik.data;

import cuik.data.models.PurchaseItem;

public class PurchaseItemRepository extends Repository<PurchaseItem> {
    public PurchaseItemRepository(SqlClient client) {
        super(client);
    }

    @Override
    protected Class<PurchaseItem> getModelClass() {
        return PurchaseItem.class;
    }

    @Override
    protected String getTableName() {
        return "PurchaseItems";
    }
}
