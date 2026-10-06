package cuik.data.models;

import cuik.data.Column;
import java.util.UUID;

public class NutritionalFact extends BaseEntity {

    @Column("menu_item_id")
    public UUID menuItemId;

    @Column
    public String portion;

    @Column
    public int calories;

    @Column
    public float protein;

    @Column
    public float fats;

    @Column("is_vegan")
    public boolean isVegan;

    @Column("is_gluten_free")
    public boolean isGlutenFree;

    public UUID getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(UUID menuItemId) {
        this.menuItemId = menuItemId;
    }

    public String getPortion() {
        return portion;
    }

    public void setPortion(String portion) {
        this.portion = portion;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public float getProtein() {
        return protein;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }

    public float getFats() {
        return fats;
    }

    public void setFats(float fats) {
        this.fats = fats;
    }

    public boolean isVegan() {
        return isVegan;
    }

    public void setVegan(boolean isVegan) {
        this.isVegan = isVegan;
    }

    public boolean isGlutenFree() {
        return isGlutenFree;
    }

    public void setGlutenFree(boolean isGlutenFree) {
        this.isGlutenFree = isGlutenFree;
    }
}
