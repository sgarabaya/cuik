package cuik.controllers;

import java.util.List;

import com.google.gson.JsonElement;

import cuik.data.MenuItemRepository;
import cuik.data.models.MenuItem;
import cuik.data.pojo.payloads.MenuItemPayload;
import cuik.server.annotations.Controller;
import cuik.utilities.Strings;
import cuik.utilities.Transform;

@Controller("/api/menu_items")
public class MenuItemController extends CrudController<MenuItem, MenuItemRepository> {
    public MenuItemController(MenuItemRepository repository) {
        super(repository);
    }

    @Override
    protected MenuItem _create(JsonElement element) throws Exception {
        // Validar: menuId, name, price como obligatorios
        var payload = Transform.fromJsonElement(element, MenuItemPayload.class);

        var menuItem = new MenuItem();

        // Obligatorios:
        menuItem.setMenuId(payload.menuId());
        menuItem.setName(payload.name());
        menuItem.setPrice(payload.price());

        // Opcionales:
        if (!Strings.isEmpty(payload.category()))
            menuItem.setCategory(payload.category());

        if (!Strings.isEmpty(payload.description()))
            menuItem.setDescription(payload.description());

        menuItem.setAvailable(payload.available() == true); // Si es null o false, setea false

        if (payload.estimatedPrepTime() != null)
            menuItem.setEstimatedPrepTime(payload.estimatedPrepTime());

        if (payload.nutritionalFacts() != null)
            menuItem.setNutritionalFacts(payload.nutritionalFacts());

        if (payload.allergens() != null && payload.allergens().length > 0)
            menuItem.setAllergens(List.of(payload.allergens()));

        return menuItem;
    }

    @Override
    protected MenuItem _update(MenuItem object, JsonElement element) throws Exception {
        var payload = Transform.fromJsonElement(element, MenuItemPayload.class);

        if (!Strings.isEmpty(payload.name()))
            object.setName(payload.name());

        if (payload.price() != null)
            object.setPrice(payload.price());

        if (!Strings.isEmpty(payload.category()))
            object.setCategory(payload.category());

        if (!Strings.isEmpty(payload.description()))
            object.setDescription(payload.description());

        if (payload.available() != null)
            object.setAvailable(payload.available() == true);

        if (payload.estimatedPrepTime() != null)
            object.setEstimatedPrepTime(payload.estimatedPrepTime());

        if (payload.nutritionalFacts() != null)
            object.setNutritionalFacts(payload.nutritionalFacts());

        if (payload.allergens() != null && payload.allergens().length > 0)
            object.setAllergens(List.of(payload.allergens()));

        return object;
    }
}
