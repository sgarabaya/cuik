package cuik.data;

import java.util.List;
import java.util.UUID;

import cuik.data.models.Purchase;
import cuik.data.models.PurchaseItem;
import cuik.data.pojo.PurchaseView;

public class PurchaseRepository extends Repository<Purchase> {
    public PurchaseRepository(SqlClient client) {
        super(client);
    }

    @Override
    protected Class<Purchase> getModelClass() {
        return Purchase.class;
    }

    @Override
    protected String getTableName() {
        return "Purchases";
    }

    public List<PurchaseView> fetchViews() throws Exception {
        var query = "SELECT"
                + "     P.id,\n"
                + "     P.created,\n"
                + "     P.user_id                   AS userId,\n"
                + "     U.name                      AS userName,\n"
                + "     U.email                     AS userEmail,\n"
                + "     SUM(PI.units)               AS items,\n"
                + "     SUM(PI.price * PI.units)    AS total\n"
                + " FROM Purchases P\n"
                + " INNER JOIN PurchaseItems PI ON PI.purchase_id = P.id\n"
                + " INNER JOIN Users U ON U.id = P.user_id\n"
                + " GROUP BY P.id;";

        return queryRaw(PurchaseView.class, query);
    }

    public List<PurchaseView> fetchViewsByUserId(String id) throws Exception {
        var query = "SELECT"
                + "     P.id,\n"
                + "     P.created,\n"
                + "     P.user_id                   AS userId,\n"
                + "     U.name                      AS userName,\n"
                + "     U.email                     AS userEmail,\n"
                + "     SUM(PI.units)               AS items,\n"
                + "     SUM(PI.price * PI.units)    AS total\n"
                + " FROM Purchases P\n"
                + " INNER JOIN PurchaseItems PI ON PI.purchase_id = P.id\n"
                + " INNER JOIN Users U ON U.id = P.user_id\n"
                + " WHERE P.user_id = ?\n"
                + " GROUP BY P.id;";

        return queryRaw(PurchaseView.class, query, id);
    }

    @Override
    public UUID create(Purchase obj) throws Exception {
        var itemsRepository = new PurchaseItemRepository(client);

        try (var conn = connect()) {
            conn.setAutoCommit(false);

            try {
                var id = super.create(conn, obj);
                for (var item : obj.getItems()) {
                    item.setPurchaseId(id);
                    itemsRepository.create(conn, item);
                }
                conn.commit();
                return id;
            } catch (Exception ex) {
                conn.rollback();
                throw ex;
            }
        }
    }
}
