package cuik.data;

import cuik.data.models.MenuItem;
import cuik.data.pojo.MenuItemSearchResult;

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
                menuId.toString());
    }

    public List<MenuItemSearchResult> search(String query) throws Exception {
        var likeQuery = "%" + query + "%";
        return queryRaw(MenuItemSearchResult.class,
                "" +
                        "SELECT\n" +
                        "    M.id AS menu_id,\n" +
                        "    M.name AS menu_name,\n" +
                        "    M.is_active AS menu_is_active,\n" +
                        "    MI.*\n" +
                        "FROM MenuItems MI\n" +
                        "    INNER JOIN Menus M ON M.id = MI.menu_id\n" +
                        "WHERE\n" +
                        "    M.is_active = 1\n" +
                        "    AND (\n" +
                        "        (M.name LIKE ?)\n" +
                        "        OR (MI.category LIKE ?)\n" +
                        "        OR (MI.description LIKE ?)\n" +
                        "        OR (MI.name LIKE ?)\n" +
                        "    );",

                // Usamos la query 4 veces:
                likeQuery, likeQuery, likeQuery, likeQuery);
    }
}
