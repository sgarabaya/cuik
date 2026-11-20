package cuik.controllers;

import cuik.data.MenuItemRepository;
import cuik.data.MenuRepository;
import cuik.data.Role;
import cuik.data.models.Menu;
import cuik.data.models.MenuItem;
import cuik.data.pojo.payloads.MenuPayload;
import cuik.server.annotations.Authenticate;
import cuik.server.annotations.Controller;
import cuik.server.annotations.Get;
import cuik.utilities.Transform;
import cuik.utilities.validation.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.google.gson.JsonElement;

@Authenticate(Role.Employee)
@Controller("/api/menus")
public class MenuController extends CrudController<Menu, MenuRepository> {
    private MenuItemRepository menuItemRepository;

    public MenuController(MenuRepository repository, MenuItemRepository menuItemRepository) {
        super(repository);
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    protected Menu _create(JsonElement element) throws Exception {
        var payload = Transform.fromJsonElement(element, MenuPayload.class);

        Validator.of(payload.name(), "nombre").notNull().notBlank().lengthBetween(2, 40).check();

        var menu = new Menu();

        menu.setName(payload.name());
        menu.setActive(false);

        return menu;
    }

    @Override
    protected Menu _update(Menu object, JsonElement element) throws Exception {
        var payload = Transform.fromJsonElement(element, MenuPayload.class);

        if (payload.name() != null) {
            Validator.of(payload.name(), "nombre").notBlank().lengthBetween(2, 40).check();
            object.setName(payload.name());
        }

        if (payload.isActive() != null)
            object.setActive(payload.isActive());

        return object;
    }

    @Override
    @Get
    @Authenticate(Role.Guest)
    public List<Menu> fetch() throws Exception {
        var menus = super.fetch();

        var items = menuItemRepository.fetch();

        var itemMap = new HashMap<UUID, ArrayList<MenuItem>>();

        for (var item : items) {
            if (!itemMap.containsKey(item.getMenuId())) {
                itemMap.put(item.getMenuId(), new ArrayList<>());
            }
            itemMap.get(item.getMenuId()).add(item);
        }

        for (var menu : menus) {
            menu.setItems(itemMap.get(menu.getId()));
        }

        return menus;
    }
}
