package cuik.data.pojo;

import cuik.data.models.MenuItem;
import cuik.data.models.NutritionalFacts;

import java.util.List;

public class MenuItemView {
    public String name;
    public String category;
    public String description;
    public NutritionalFacts nutritionalFacts;
    public List<String> allergens;
    public float price;
    public boolean available;
    public int estimatedPrepTime;

    public static MenuItemView from(MenuItem menuItem, NutritionalFacts nutritionalFacts) {
        var view = new MenuItemView();
        view.name = menuItem.getName();
        view.category = menuItem.getCategory();
        view.description = menuItem.getDescription();
        view.price = menuItem.getPrice();
        view.available = menuItem.isAvailable();
        view.nutritionalFacts = nutritionalFacts;
        view.estimatedPrepTime = menuItem.getEstimatedPrepTime();
        return view;
    }
}
