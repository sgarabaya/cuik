package cuik.controllers;

import com.google.gson.JsonElement;

import cuik.data.AllergenRepository;
import cuik.data.models.Allergen;
import cuik.data.pojo.payloads.AllergenPayload;
import cuik.server.annotations.Controller;
import cuik.utilities.Transform;

@Controller("/api/allergens")
public class AllergenController extends CrudController<Allergen, AllergenRepository> {
    public AllergenController(AllergenRepository repository) {
        super(repository);
    }

    @Override
    protected Allergen _create(JsonElement element) throws Exception {
        var payload = Transform.fromJsonElement(element, AllergenPayload.class);
        var allergen = new Allergen();
        allergen.setName(payload.name());
        return allergen;
    }

    @Override
    protected Allergen _update(Allergen object, JsonElement element) throws Exception {
        var payload = Transform.fromJsonElement(element, AllergenPayload.class);
        object.setName(payload.name());
        return object;
    }
}
