package cuik.data.pojo;

import cuik.data.models.MenuItem;
import cuik.data.models.NutritionalFact;

import java.util.List;

public class MenuItemView {
    public String description;
    public NutritionalFact nutritionalFacts;
    public List<String> allergens;
    public float price;
    public boolean available;

    public static MenuItemView from(MenuItem menuItem, NutritionalFact nutritionalFacts) {
        var view = new MenuItemView();
        view.description = menuItem.getDescription();
        view.price = menuItem.getPrice();
        view.available = menuItem.isAvailable();
        view.nutritionalFacts = nutritionalFacts;
        return view;
    }
}
