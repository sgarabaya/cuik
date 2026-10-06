package cuik.data.pojo;

import cuik.data.models.MenuItem;
import java.util.List;

public class MenuItemView {

    public String description;
    public List<String> allergens;
    public List<String> ingredients;
    public double price;
    public boolean available;
    public List<String> images;

    public static MenuItemView from(MenuItem menuItem, List<String> imageUrls) {
        var view = new MenuItemView();
        view.description = menuItem.getDescription();
        view.price = menuItem.getPrice();
        view.available = menuItem.isAvailable();
        view.images = imageUrls;
        return view;
    }
}
