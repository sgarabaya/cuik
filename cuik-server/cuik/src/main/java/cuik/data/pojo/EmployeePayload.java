package cuik.data.pojo;

import java.util.UUID;

public record EmployeePayload(UUID id, String name, String email, String password, Boolean isAdmin) {
}
