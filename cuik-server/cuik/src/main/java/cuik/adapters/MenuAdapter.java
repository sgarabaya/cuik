package cuik.adapters;

import java.util.List;
import java.util.UUID;

import cuik.data.MenuItemRepository;
import cuik.data.MenuRepository;
import cuik.data.models.Menu;
import cuik.data.pojo.MenuItemSearchResult;
import cuik.data.pojo.MenuItemView;
import cuik.data.pojo.MenuView;
import cuik.exceptions.CuikNotFoundException;

public class MenuAdapter {
    private final MenuRepository menuRepository;
    private final MenuItemRepository menuItemRepository;

    public MenuAdapter(MenuRepository menuRepository, MenuItemRepository menuItemRepository) {
        this.menuRepository = menuRepository;
        this.menuItemRepository = menuItemRepository;
    }

    public List<Menu> listMenus() throws Exception {
        return menuRepository.fetch();
    }

    public MenuView fetchMenu(UUID id) throws Exception {
        var menu = menuRepository.findById(id);

        if (menu == null)
            throw new CuikNotFoundException();

        var menuItems = menuItemRepository.fetchByMenuId(id);

        var menuItemViews = menuItems
                .stream()
                .map(mi -> MenuItemView.from(mi, null))
                .toList();

        return MenuView.from(menu, menuItemViews);
    }

    public List<MenuItemSearchResult> search(String query) throws Exception {
        return menuItemRepository.search(query);
    }
}
