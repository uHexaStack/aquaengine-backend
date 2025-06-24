package com.qu3dena.aquaengine.backend.iam.infrastructure.tokens.jwt;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import com.qu3dena.aquaengine.backend.iam.application.internal.outboundservices.tokens.TokenService;


/**
 * BearerTokenService
 * <p>
 *     Interface that defines the methods to generate and extract Bearer Tokens.
 *     This interface extends the TokenService interface.
 * </p>
 */
public interface BearerTokenService extends TokenService {
    /**
     * Method to extract the Bearer Token from a HttpServletRequest.
     * @param token {@link HttpServletRequest} The request from which the token will be extracted.
     * @return String containing the Bearer Token.
     */
    String getBearerTokenFrom(HttpServletRequest token);

}