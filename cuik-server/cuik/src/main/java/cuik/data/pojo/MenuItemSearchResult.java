package cuik.data.pojo;

import java.util.List;
import java.util.UUID;

import cuik.data.Column;

public class MenuItemSearchResult {
    @Column("menu_id")
    public UUID menuId;

    @Column("menu_name")
    public String menuName;

    @Column
    public String name;

    @Column
    public String description;

    @Column
    public List<String> allergens;

    @Column
    public List<String> ingredients;

    @Column
    public float price;

    @Column
    public boolean available;

    @Column
    public List<String> images;
}
