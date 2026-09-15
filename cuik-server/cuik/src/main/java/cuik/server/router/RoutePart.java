package cuik.server.router;

public record RoutePart(boolean isWildcard, String part) {
    private RoutePart() {
        this(false, null);
    }

    public RoutePart(String part) {
        this(false, part);
    }

    public static RoutePart wildcard = new RoutePart();
}
