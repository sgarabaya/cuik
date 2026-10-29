package cuik.data.models;

import cuik.data.Column;

import java.util.List;
import java.util.UUID;

public class MenuItem extends BaseEntity {
    @Column("menu_id")
    private UUID menuId;

    @Column
    private String name;

    @Column
    private String category;

    @Column
    private String description;

    @Column
    private float price;

    @Column
    private boolean available;

    @Column("estimated_prep_time")
    private int estimatedPrepTime;

    private NutritionalFacts nutritionalFacts;

    private List<Allergen> allergens;

    public UUID getMenuId() {
        return menuId;
    }

    public void setMenuId(UUID menuId) {
        this.menuId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getEstimatedPrepTime() {
        return estimatedPrepTime;
    }

    public void setEstimatedPrepTime(int estimatedPrepTime) {
        this.estimatedPrepTime = estimatedPrepTime;
    }

    public NutritionalFacts getNutritionalFacts() {
        return nutritionalFacts;
    }

    public void setNutritionalFacts(NutritionalFacts nutritionalFacts) {
        this.nutritionalFacts = nutritionalFacts;
    }

    public List<Allergen> getAllergens() {
        return allergens;
    }

    public void setAllergens(List<Allergen> allergens) {
        this.allergens = allergens;
    }
}
