package cuik.controllers;

import com.google.gson.JsonElement;

import cuik.data.EmployeeRepository;
import cuik.data.Role;
import cuik.data.models.Employee;
import cuik.data.pojo.payloads.EmployeePayload;
import cuik.server.annotations.Authenticate;
import cuik.server.annotations.Controller;
import cuik.utilities.Crypto;
import cuik.utilities.Transform;
import cuik.utilities.validation.Validator;

@Authenticate(Role.Admin) // admin only
@Controller("/api/employees")
public class EmployeeController extends CrudController<Employee, EmployeeRepository> {
    public EmployeeController(EmployeeRepository repository) {
        super(repository);
    }

    @Override
    protected Employee _create(JsonElement element) throws Exception {
        var payload = Transform.fromJsonElement(element, EmployeePayload.class);

        Validator.of(payload.name(), "nombre").notNull().notBlank().lengthBetween(2, 256).check();
        Validator.of(payload.email(), "email").notNull().notBlank().email().maxLength(256).check();
        Validator.of(payload.password(), "contraseña").notNull().notBlank().lengthBetween(8, 128).check();

        var employee = new Employee();
        employee.setName(payload.name());
        employee.setEmail(payload.email());
        employee.setPasswordHash(Crypto.hash(payload.password()));
        employee.setIsAdmin(payload.isAdmin() == true);

        return employee;
    }

    @Override
    protected Employee _update(Employee object, JsonElement element) throws Exception {
        var payload = Transform.fromJsonElement(element, EmployeePayload.class);

        if (payload.name() != null) {
            Validator.of(payload.name(), "nombre").notBlank().lengthBetween(2, 256).check();
            object.setName(payload.name());
        }

        if (payload.email() != null) {
            Validator.of(payload.email(), "email").notBlank().email().maxLength(256).check();
            object.setEmail(payload.email());
        }

        if (payload.password() != null) {
            Validator.of(payload.password(), "contraseña").notBlank().lengthBetween(8, 128).check();
            object.setPasswordHash(Crypto.hash(payload.password()));
        }

        if (payload.isAdmin() != null) // Boolean wrapper can be null
            object.setIsAdmin(payload.isAdmin() == true);

        return object;
    }
}
