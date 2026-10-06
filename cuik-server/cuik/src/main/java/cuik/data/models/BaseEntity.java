package cuik.data.models;

import cuik.data.Column;
import java.time.LocalDateTime;
import java.util.UUID;

public abstract class BaseEntity {

    @Column
    protected UUID id;

    @Column
    protected LocalDateTime created;

    @Column
    protected LocalDateTime updated;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }
}
