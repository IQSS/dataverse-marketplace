package edu.harvard.iq.dataverse.marketplace.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import edu.harvard.iq.dataverse.marketplace.payload.ServerMessageResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;

@RestControllerAdvice
public class ErrorResponseController {
    
    
    
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ServerMessageResponse noResourceFoundExceptionResponse(Exception e) {
        return new ServerMessageResponse(HttpStatus.NOT_FOUND, e);
    }
    
    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ServerMessageResponse badRequestExceptionResponse(Exception e) {
        return new ServerMessageResponse(HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ServerMessageResponse unauthorizedResponse(Exception e) {
        return new ServerMessageResponse(HttpStatus.UNAUTHORIZED, e);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ServerMessageResponse noRoleResponse(Exception e) {
        return new ServerMessageResponse(HttpStatus.UNAUTHORIZED, e);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ServerMessageResponse badCredentialsResponse(Exception e) {
        return new ServerMessageResponse(HttpStatus.UNAUTHORIZED, e);
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ServerMessageResponse errorResponse(Exception e) {
        return new ServerMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR, e);
    }



}
