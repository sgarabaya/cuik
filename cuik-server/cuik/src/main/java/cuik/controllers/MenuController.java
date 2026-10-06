package cuik.controllers;

import cuik.data.MenuItemRepository;
import cuik.data.MenuRepository;
import cuik.data.models.Menu;
import cuik.data.pojo.MenuItemView;
import cuik.data.pojo.MenuView;
import cuik.server.annotations.Controller;
import cuik.server.annotations.Get;
import java.util.List;
import java.util.UUID;

@Controller("/api/menu")
public class MenuController {

    private final MenuRepository menuRepository;
    private final MenuItemRepository menuItemRepository;

    public MenuController(
        MenuRepository menuRepository,
        MenuItemRepository menuItemRepository
    ) {
        this.menuRepository = menuRepository;
        this.menuItemRepository = menuItemRepository;
    }

    @Get
    public List<Menu> listMenus() throws Exception {
        return menuRepository.fetch();
    }

    @Get("{id}")
    public MenuView fetchMenu(String id) throws Exception {
        var menuId = UUID.fromString(id);
        var menu = menuRepository.findById(menuId);
        var menuItems = menuItemRepository.fetchByMenuId(menuId);

        var menuItemViews = menuItems
            .stream()
            .map(mi -> MenuItemView.from(mi, null))
            .toList();

        return MenuView.from(menu, menuItemViews);
    }
}
