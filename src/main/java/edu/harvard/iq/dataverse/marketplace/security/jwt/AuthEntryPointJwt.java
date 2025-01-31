package edu.harvard.iq.dataverse.marketplace.security.jwt;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * This class is used to handle unauthorized requests.
 */
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint{

    Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);
    
    /**
     * This method will be triggerd anytime unauthenticated User 
     * requests a secured HTTP resource and an AuthenticationException is thrown.
     */
    @Override
    public void commence(   HttpServletRequest request, 
                            HttpServletResponse response,
                            AuthenticationException authException) throws IOException, ServletException {

            logger.error("Unauthorized error: {}", authException.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
    }

}
