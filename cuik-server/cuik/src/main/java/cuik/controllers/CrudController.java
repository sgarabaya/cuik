package cuik.controllers;

import java.util.List;
import java.util.UUID;

import com.google.gson.JsonElement;

import cuik.data.Repository;
import cuik.data.models.BaseEntity;
import cuik.server.annotations.Delete;
import cuik.server.annotations.FromBody;
import cuik.server.annotations.Get;
import cuik.server.annotations.Post;
import cuik.server.annotations.Put;

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
        // TODO: validar id es correcto uuid
        var uuid = UUID.fromString(id);
        return repository.fetchById(uuid);
    }

    @Post
    public UUID create(@FromBody JsonElement payload) throws Exception {
        return repository.create(_create(payload));
    }

    @Put("{id}")
    public boolean update(String id, @FromBody JsonElement payload) throws Exception {
        // TODO: validar id es correcto uuid
        var uuid = UUID.fromString(id);
        var object = repository.fetchById(uuid);

        return repository.update(_update(object, payload));
    }

    @Delete("{id}")
    public void delete(String id) throws Exception {
        // TODO: validar id es correcto uuid
        var uuid = UUID.fromString(id);
        repository.delete(uuid);
    }
}
