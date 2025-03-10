package com.github.repos.viewer.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.Map;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<WebApplicationException> {

    private static final String USER_NOT_FOUND_ERROR = " The requested GitHub user cannot be found.";

    @Override
    public Response toResponse(WebApplicationException exception) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", exception.getMessage() + USER_NOT_FOUND_ERROR);
        errorResponse.put("status", String.valueOf(exception.getResponse().getStatus()));

        return Response.status(exception.getResponse().getStatus())
                .type(MediaType.APPLICATION_JSON)
                .entity(errorResponse)
                .build();
    }
}
