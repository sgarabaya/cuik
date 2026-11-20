package cuik.controllers;

import java.util.List;

import com.google.gson.JsonElement;

import cuik.data.MenuItemRepository;
import cuik.data.Role;
import cuik.data.models.MenuItem;
import cuik.data.pojo.payloads.MenuItemPayload;
import cuik.server.annotations.Authenticate;
import cuik.server.annotations.Controller;
import cuik.utilities.Transform;
import cuik.utilities.validation.Validator;

@Authenticate(Role.Employee)
@Controller("/api/menu_items")
public class MenuItemController extends CrudController<MenuItem, MenuItemRepository> {
    public MenuItemController(MenuItemRepository repository) {
        super(repository);
    }

    @Override
    protected MenuItem _create(JsonElement element) throws Exception {
        var payload = Transform.fromJsonElement(element, MenuItemPayload.class);

        Validator.of(payload.menuId(), "id del menú").notNull().uuid().check();
        Validator.of(payload.name(), "nombre").notNull().notBlank().lengthBetween(2, 40).check();
        Validator.of(payload.price(), "precio").notNull().positive().max(100000).check();

        var menuItem = new MenuItem();

        menuItem.setMenuId(payload.menuId());
        menuItem.setName(payload.name());
        menuItem.setPrice(payload.price());

        if (payload.category() != null && !payload.category().isBlank()) {
            Validator.of(payload.category(), "categoría").maxLength(64).check();
            menuItem.setCategory(payload.category());
        }

        if (payload.description() != null && !payload.description().isBlank()) {
            Validator.of(payload.description(), "descripción").maxLength(2000).check();
            menuItem.setDescription(payload.description());
        }

        menuItem.setAvailable(payload.available() == true);

        if (payload.estimatedPrepTime() != null) {
            Validator.of(payload.estimatedPrepTime(), "tiempo de preparación").min(0).check();
            menuItem.setEstimatedPrepTime(payload.estimatedPrepTime());
        }

        if (payload.nutritionalFacts() != null)
            menuItem.setNutritionalFacts(payload.nutritionalFacts());

        if (payload.allergens() != null && payload.allergens().length > 0)
            menuItem.setAllergens(List.of(payload.allergens()));

        return menuItem;
    }

    @Override
    protected MenuItem _update(MenuItem object, JsonElement element) throws Exception {
        var payload = Transform.fromJsonElement(element, MenuItemPayload.class);

        if (payload.menuId() != null) {
            Validator.of(payload.menuId(), "id del menú").uuid().check();
            object.setMenuId(payload.menuId());
        }

        if (payload.name() != null) {
            Validator.of(payload.name(), "nombre").notBlank().lengthBetween(2, 40).check();
            object.setName(payload.name());
        }

        if (payload.price() != null) {
            Validator.of(payload.price(), "precio").positive().max(100000).check();
            object.setPrice(payload.price());
        }

        if (payload.category() != null && !payload.category().isBlank()) {
            Validator.of(payload.category(), "categoría").maxLength(64).check();
            object.setCategory(payload.category());
        }

        if (payload.description() != null && !payload.description().isBlank()) {
            Validator.of(payload.description(), "descripción").maxLength(2000).check();
            object.setDescription(payload.description());
        }

        if (payload.available() != null)
            object.setAvailable(payload.available() == true);

        if (payload.estimatedPrepTime() != null) {
            Validator.of(payload.estimatedPrepTime(), "tiempo de preparación").min(0).check();
            object.setEstimatedPrepTime(payload.estimatedPrepTime());
        }

        if (payload.nutritionalFacts() != null)
            object.setNutritionalFacts(payload.nutritionalFacts());

        if (payload.allergens() != null && payload.allergens().length > 0)
            object.setAllergens(List.of(payload.allergens()));

        return object;
    }
}
