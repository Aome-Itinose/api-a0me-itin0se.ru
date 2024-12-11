package org.aome.cvapi.util.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(String message) {
        super(message);
    }
}
