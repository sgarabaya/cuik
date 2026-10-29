package cuik.controllers;

import java.util.UUID;

import cuik.data.MenuItemRepository;
import cuik.data.MenuRepository;
import cuik.data.pojo.MenuItemView;
import cuik.data.pojo.MenuView;
import cuik.data.pojo.UserView;
import cuik.server.ViewHandler;
import cuik.server.annotations.Controller;
import cuik.server.annotations.View;

@Controller("/")
public class ViewsController extends BaseController {
    private final MenuRepository menuRepository;
    private final MenuItemRepository menuItemRepository;

    public ViewsController(MenuRepository menuRepository, MenuItemRepository menuItemRepository) {
        this.menuRepository = menuRepository;
        this.menuItemRepository = menuItemRepository;

    }

    @View
    public String index() throws Exception {
        var user = new UserView();
        user.setName("Santi");
        user.setRoles(new String[] { "admin", "user", "employee" });

        return ViewHandler.render("users.ftl", user);
    }

    @View("menu/{id}")
    public String viewMenu(String id) throws Exception {
        var menuId = UUID.fromString(id);
        var menu = menuRepository.findById(menuId);
        var menuItems = menuItemRepository.fetchByMenuId(menuId);

        var menuView = MenuView.from(menu, menuItems.stream().map(mi -> MenuItemView.from(mi, null)).toList());

        return ViewHandler.render("menu.ftl", menuView);
    }

    @View("menu")
    public String viewMenuEditor() throws Exception {
        // var menuId = UUID.fromString(id);
        // var menu = menuRepository.findById(menuId);
        // var menuItems = menuItemRepository.fetchByMenuId(menuId);

        // var menuView = MenuView.from(menu, menuItems.stream().map(mi ->
        // MenuItemView.from(mi, null)).toList());

        return ViewHandler.render("menu.ftl", null);
    }

}
