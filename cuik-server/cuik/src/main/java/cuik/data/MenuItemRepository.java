package cuik.data;

import cuik.data.models.MenuItem;
import java.util.List;
import java.util.UUID;

public class MenuItemRepository extends Repository<MenuItem> {

    public MenuItemRepository(SqlClient client) {
        super(client);
    }

    @Override
    protected Class<MenuItem> getModelClass() {
        return MenuItem.class;
    }

    @Override
    protected String getTableName() {
        return "MenuItems";
    }

    public List<MenuItem> fetchByMenuId(UUID menuId) throws Exception {
        return query(
            "SELECT * FROM MenuItems WHERE menu_id = ?;",
            menuId.toString()
        );
    }
}
