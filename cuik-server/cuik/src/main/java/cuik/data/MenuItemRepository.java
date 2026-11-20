package cuik.data;

import java.util.List;
import java.util.UUID;

import cuik.data.models.MenuItem;
import cuik.data.pojo.MenuItemSearchResult;

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
        var queryString = """
                SELECT
                    MI.*,
                    NF.id AS NutritionalFact__id,
                    NF.description AS NutritionalFact__description,
                    NF.portion AS NutritionalFact__portion,
                    NF.calories AS NutritionalFact__calories,
                    NF.protein AS NutritionalFact__protein,
                    NF.fats AS NutritionalFact__fats,
                    NF.is_vegan AS NutritionalFact__is_vegan,
                    NF.is_gluten_free AS NutritionalFact__is_gluten_free
                FROM MenuItems MI
                LEFT JOIN NutritionalFacts NF ON NF.menu_item_id = MI.id
                WHERE MI.menu_id = ?;
                            """;

        return query(queryString, menuId.toString());
    }

    public List<MenuItemSearchResult> search(String search) throws Exception {
        var likeSearch = "%" + search + "%";
        var queryString = """
                SELECT
                    M.id AS menu_id,
                    M.name AS menu_name,
                    M.is_active AS menu_is_active,
                    MI.*
                FROM MenuItems MI
                    INNER JOIN Menus M ON M.id = MI.menu_id
                WHERE
                    M.is_active = 1
                    AND (
                        (M.name LIKE ?)
                        OR (MI.category LIKE ?)
                        OR (MI.description LIKE ?)
                        OR (MI.name LIKE ?)
                    );""";

        return queryRaw(MenuItemSearchResult.class, queryString,
                // Usamos la query 4 veces:
                likeSearch, likeSearch, likeSearch, likeSearch);
    }
}
