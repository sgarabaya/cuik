package cuik.data.pojo;

import cuik.data.models.Menu;
import java.util.List;
import java.util.UUID;

public class MenuView {

    public UUID id;
    public String name;
    public boolean isActive;
    public List<MenuItemView> items;

    public static MenuView from(Menu menu, List<MenuItemView> menuItems) {
        var view = new MenuView();
        view.id = menu.getId();
        view.name = menu.getName();
        view.isActive = menu.isActive();
        view.items = menuItems;
        return view;
    }
}
