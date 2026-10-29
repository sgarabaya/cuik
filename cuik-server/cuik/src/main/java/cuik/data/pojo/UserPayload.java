package cuik.data.pojo;

import java.util.UUID;

public record UserPayload(UUID id, String name, String email, String password) {}
