package cuik.data;

import cuik.data.models.Employee;
import cuik.data.models.User;

public class EmployeeRepository extends Repository<Employee> {
    public EmployeeRepository(SqlClient client) {
        super(client);
    }

    @Override
    protected Class<Employee> getModelClass() {
        return Employee.class;
    }

    @Override
    protected String getTableName() {
        return "Employees";
    }

    public Employee findByEmail(String email) throws Exception {
        return querySingle("SELECT * FROM Employees WHERE email = ?", email);
    }
}
