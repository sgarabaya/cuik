package cuik.controllers;

import cuik.adapters.MenuAdapter;
import cuik.data.models.Menu;
import cuik.data.pojo.MenuItemSearchResult;
import cuik.data.pojo.MenuView;
import cuik.server.annotations.Controller;
import cuik.server.annotations.Get;
import java.util.List;
import java.util.UUID;

@Controller("/api/menu")
public class MenuController {
    private final MenuAdapter menuAdapter;

    public MenuController(MenuAdapter menuAdapter) {
        this.menuAdapter = menuAdapter;
    }

    @Get
    public List<Menu> listMenus() throws Exception {
        return menuAdapter.listMenus();
    }

    @Get("{id}")
    public MenuView fetchMenu(String id) throws Exception {
        var menuId = UUID.fromString(id);
        return menuAdapter.fetchMenu(menuId);
    }

    @Get("search")
    public List<MenuItemSearchResult> searchMenuItems(String query) throws Exception {
        return menuAdapter.search(query);
    }
}
