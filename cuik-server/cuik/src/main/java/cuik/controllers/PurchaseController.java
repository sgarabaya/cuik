package cuik.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.JsonElement;

import cuik.data.PurchaseRepository;
import cuik.data.Role;
import cuik.data.models.Purchase;
import cuik.data.models.PurchaseItem;
import cuik.data.pojo.PurchaseView;
import cuik.data.pojo.payloads.PurchasePayload;
import cuik.server.JwtAuth;
import cuik.server.annotations.Authenticate;
import cuik.server.annotations.Controller;
import cuik.server.annotations.FromBody;
import cuik.server.annotations.Get;
import cuik.server.annotations.Post;
import cuik.utilities.Transform;
import cuik.utilities.validation.Validator;

@Authenticate(Role.Employee)
@Controller("/api/purchases")
public class PurchaseController extends BaseController {
    private final PurchaseRepository repository;

    public PurchaseController(PurchaseRepository repository) {
        this.repository = repository;
    }

    protected Purchase _create(JsonElement element) throws Exception {
        var payload = Transform.fromJsonElement(element, PurchasePayload.class);

        Validator.of(payload.userId(), "id del usuario").notNull().uuid().check();
        Validator.of(payload.items(), "items").notNull().minLength(1).check();

        var purchase = new Purchase();
        purchase.setUserId(payload.userId());

        var purchaseItems = new ArrayList<PurchaseItem>();
        for (var payloadItem : payload.items()) {
            Validator.of(payloadItem.menuItemId(), "id del item del Menu").notNull().uuid().check();
            Validator.of(payloadItem.price(), "precio").notNull().min(0.1f).check();
            Validator.of(payloadItem.units(), "unidades").notNull().min(1).check();

            var item = new PurchaseItem();
            item.setMenuItemId(payloadItem.menuItemId());
            item.setPrice(payloadItem.price());
            item.setUnits(payloadItem.units());
            purchaseItems.add(item);
        }
        purchase.setItems(purchaseItems);

        return purchase;
    }

    @Post
    @Authenticate(Role.User)
    public UUID create(@FromBody JsonElement payload) throws Exception {
        var entry = _create(payload);
        return repository.create(entry);
    }

    @Get
    @Authenticate(Role.User)
    public List<PurchaseView> fetch() throws Exception {
        var token = JwtAuth.parseToken(context.getBearerToken());

        if (token.role().satisfies(Role.Employee))
            return repository.fetchViews();
        return repository.fetchViewsByUserId(token.id());
    }
}
