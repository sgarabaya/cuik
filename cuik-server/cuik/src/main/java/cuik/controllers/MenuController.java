package cuik.controllers;

import cuik.adapters.MenuAdapter;
import cuik.data.MenuItemRepository;
import cuik.data.MenuRepository;
import cuik.data.models.Menu;
import cuik.data.models.MenuItem;
import cuik.data.pojo.payloads.MenuPayload;
import cuik.server.annotations.Controller;
import cuik.server.annotations.Get;
import cuik.utilities.Strings;
import cuik.utilities.Transform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.google.gson.JsonElement;

@Controller("/api/menus")
public class MenuController extends CrudController<Menu, MenuRepository> {
    private MenuItemRepository menuItemRepository;

    public MenuController(MenuRepository repository, MenuItemRepository menuItemRepository) {
        super(repository);
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    protected Menu _create(JsonElement element) throws Exception {
        // Validar que tenga name y isActive = false (en creacion el menu no se activa)
        var payload = Transform.fromJsonElement(element, MenuPayload.class);

        var menu = new Menu();

        menu.setName(payload.name());
        menu.setActive(false);

        return menu;
    }

    @Override
    protected Menu _update(Menu object, JsonElement element) throws Exception {
        var payload = Transform.fromJsonElement(element, MenuPayload.class);

        if (!Strings.isEmpty(payload.name()))
            object.setName(payload.name());

        if (payload.isActive() != null)
            object.setActive(payload.isActive());

        return object;
    }

    @Override
    @Get
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

    @Get("{id}/items")
    public List<MenuItem> getItems(String id) throws Exception {
        // TODO: validar id
        var menuId = Strings.safeParseUUID(id);

        return repository.fetchItems(menuId);
    }
}
