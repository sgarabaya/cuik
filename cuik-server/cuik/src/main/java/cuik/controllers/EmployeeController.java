package cuik.controllers;

import com.google.gson.JsonElement;

import cuik.data.EmployeeRepository;
import cuik.data.models.Employee;
import cuik.data.pojo.payloads.EmployeePayload;
import cuik.exceptions.CuikValidationException;
import cuik.server.annotations.Controller;
import cuik.utilities.Crypto;
import cuik.utilities.Strings;
import cuik.utilities.Transform;

@Controller("/api/employees")
public class EmployeeController extends CrudController<Employee, EmployeeRepository> {
    public EmployeeController(EmployeeRepository repository) {
        super(repository);
    }

    @Override
    protected Employee _create(JsonElement element) throws Exception {
        var payload = Transform.fromJsonElement(element, EmployeePayload.class);

        if (payload.password() == null || payload.password().isBlank())
            throw new CuikValidationException("La contrasena es requerida");

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

        if (!Strings.isEmpty(payload.name()))
            object.setName(payload.name());

        if (!Strings.isEmpty(payload.email()))
            object.setEmail(payload.email());

        if (!Strings.isEmpty(payload.password()))
            object.setPasswordHash(Crypto.hash(payload.password()));

        if (payload.isAdmin() != null) // Boolean (con mayuscula) puede ser null
            object.setIsAdmin(payload.isAdmin() == true);

        return object;
    }
}
