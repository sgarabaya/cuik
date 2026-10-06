package cuik.data;

import cuik.data.models.Menu;

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
}
