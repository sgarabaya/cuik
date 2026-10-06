package cuik.data.models;

import cuik.data.Column;
import java.util.UUID;

public class Allergen {

    @Column
    private UUID id;

    @Column
    private String name;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
