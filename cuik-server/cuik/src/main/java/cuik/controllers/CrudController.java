package cuik.controllers;

import java.util.List;
import java.util.UUID;

import com.google.gson.JsonElement;

import cuik.data.Repository;
import cuik.data.models.BaseEntity;
import cuik.exceptions.CuikNotFoundException;
import cuik.exceptions.CuikValidationException;
import cuik.server.annotations.Delete;
import cuik.server.annotations.FromBody;
import cuik.server.annotations.Get;
import cuik.server.annotations.Post;
import cuik.server.annotations.Put;
import cuik.utilities.validation.Validator;

public abstract class CrudController<T extends BaseEntity, R extends Repository<T>>
        extends BaseController {
    protected R repository;

    protected CrudController(R repository) {
        this.repository = repository;
    }

    protected abstract T _create(JsonElement element) throws Exception;

    protected abstract T _update(T object, JsonElement element) throws Exception;

    @Get
    public List<T> fetch() throws Exception {
        return repository.fetch();
    }

    @Get("{id}")
    public T fetchById(String id) throws Exception {
        var entity = repository.fetchById(parseId(id));
        if (entity == null)
            throw new CuikNotFoundException();
        return entity;
    }

    @Post
    public UUID create(@FromBody JsonElement payload) throws Exception {
        return repository.create(_create(payload));
    }

    @Put("{id}")
    public boolean update(String id, @FromBody JsonElement payload) throws Exception {
        var object = repository.fetchById(parseId(id));
        if (object == null)
            throw new CuikNotFoundException();

        return repository.update(_update(object, payload));
    }

    @Delete("{id}")
    public void delete(String id) throws Exception {
        repository.delete(parseId(id));
    }

    private UUID parseId(String id) throws CuikValidationException {
        Validator.of(id, "id").notNull().uuid().check();
        return UUID.fromString(id);
    }
}
