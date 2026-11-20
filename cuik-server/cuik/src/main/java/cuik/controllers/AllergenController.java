package cuik.controllers;

import com.google.gson.JsonElement;

import cuik.data.AllergenRepository;
import cuik.data.Role;
import cuik.data.models.Allergen;
import cuik.data.pojo.payloads.AllergenPayload;
import cuik.server.annotations.Authenticate;
import cuik.server.annotations.Controller;
import cuik.utilities.Transform;
import cuik.utilities.validation.Validator;

@Authenticate(Role.Employee)
@Controller("/api/allergens")
public class AllergenController extends CrudController<Allergen, AllergenRepository> {
    public AllergenController(AllergenRepository repository) {
        super(repository);
    }

    @Override
    protected Allergen _create(JsonElement element) throws Exception {
        var payload = Transform.fromJsonElement(element, AllergenPayload.class);

        Validator.of(payload.name(), "nombre").notNull().notBlank().lengthBetween(2, 40).check();

        var allergen = new Allergen();
        allergen.setName(payload.name());
        return allergen;
    }

    @Override
    protected Allergen _update(Allergen object, JsonElement element) throws Exception {
        var payload = Transform.fromJsonElement(element, AllergenPayload.class);

        Validator.of(payload.name(), "nombre").notNull().notBlank().lengthBetween(2, 40).check();
        object.setName(payload.name());
        return object;
    }
}
