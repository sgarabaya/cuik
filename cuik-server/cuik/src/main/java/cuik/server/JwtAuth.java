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

    public static void authorize(HttpContext context, Role required) throws CuikValidationException {
        if (required == null)
            return;

        // Admin > Employee > User > Guest (no auth)
        var role = Role.Guest;
        var token = context.getBearerToken();

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
        }

        if (role.satisfies(required))
            return;

        if (token == null)
            throw new CuikValidationException(Messages.AuthenticationRequired, 401);

        throw new CuikValidationException(Messages.InsufficientPermissions, 403);
    }
}
