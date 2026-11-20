package cuik.server;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import cuik.data.Role;
import cuik.exceptions.CuikValidationException;
import cuik.utilities.Configuration;
import cuik.utilities.Messages;

public abstract class JwtAuth {
    private static final JWTVerifier VERIFIER = JWT
            .require(Algorithm.HMAC512(Configuration.getJwtSecret()))
            .build();

    private JwtAuth() {
    }

    public static Token parseToken(String token) throws CuikValidationException {
        var role = Role.Guest; // Admin > Employee > User > Guest (no auth)

        if (token != null) {
            DecodedJWT jwt;
            try {
                jwt = VERIFIER.verify(token);
            } catch (JWTVerificationException ex) {
                throw new CuikValidationException(Messages.TokenExpiredOrInvalid, 401);
            }

            role = Role.fromClaim(jwt.getClaim("role").asString());
            if (role == null)
                throw new CuikValidationException(Messages.InvalidToken, 401);

            var id = jwt.getClaim("id").asString();
            var email = jwt.getClaim("email").asString();

            return new Token(role, id, email);
        }

        return new Token(role, null, null);
    }

    public static void authorize(HttpContext context, Role required) throws CuikValidationException {
        if (required == null)
            return;

        var token = context.getBearerToken();

        if (token == null && required != Role.Guest)
            throw new CuikValidationException(Messages.AuthenticationRequired, 401);

        var parsed = parseToken(token);

        if (parsed.role().satisfies(required))
            return;

        throw new CuikValidationException(Messages.InsufficientPermissions, 403);
    }
}
