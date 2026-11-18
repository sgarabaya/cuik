package cuik.data.models;

import cuik.data.Column;

public class Allergen extends BaseEntity {
    @Column
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
