package cuik.data;

public enum Role {
    Admin(4),
    Employee(3),
    User(2),
    Guest(1);

    private final int level;

    Role(int level) {
        this.level = level;
    }

    // Admin > Employee > User > Guest
    public boolean satisfies(Role required) {
        return required != null && level >= required.level;
    }

    // Maps a JWT "role" claim (e.g. "admin") to a Role.
    public static Role fromClaim(String value) {
        if (value == null || value.isBlank())
            return null;

        for (Role role : values())
            if (role.name().equalsIgnoreCase(value))
                return role;

        return null;
    }
}
