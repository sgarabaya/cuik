package cuik.server;

import cuik.data.Role;

public record Token(Role role, String id, String email) {
}