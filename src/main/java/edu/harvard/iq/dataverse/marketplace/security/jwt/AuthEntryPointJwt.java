package edu.harvard.iq.dataverse.marketplace.security.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            Map<String, Object> body = new LinkedHashMap<>();
            String timestamp = ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            body.put("timestamp", timestamp);
            body.put("code", HttpServletResponse.SC_UNAUTHORIZED);
            body.put("Status", HttpStatus.UNAUTHORIZED.name());
            body.put("message", "Unauthorized request");
            body.put("details", "The user is not authorized to access the requested resource.");

            final ObjectMapper mapper = new ObjectMapper();

            mapper.writeValue(response.getOutputStream(), body);

    }

}
