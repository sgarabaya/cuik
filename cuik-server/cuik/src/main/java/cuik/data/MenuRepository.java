package cuik.data;

import java.util.List;
import java.util.UUID;

import cuik.data.models.Menu;
import cuik.data.models.MenuItem;

public class MenuRepository extends Repository<Menu> {

    public MenuRepository(SqlClient client) {
        super(client);
    }

    @Override
    protected Class<Menu> getModelClass() {
        return Menu.class;
    }

    @Override
    protected String getTableName() {
        return "Menus";
    }

    @Override
    public Menu fetchById(UUID id) throws Exception {
        return super.fetchById(id);
    }

    public List<MenuItem> fetchItems(UUID id) throws Exception {
        return queryRaw(MenuItem.class, "SELECT * FROM MenuItems WHERE menu_id = ?", id.toString());
    }
}
