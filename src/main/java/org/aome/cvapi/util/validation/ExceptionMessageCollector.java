package org.aome.cvapi.util.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
public class ExceptionMessageCollector {

    public String collectMessage(BindingResult bindingResult) {
        StringBuffer message = new StringBuffer();
        bindingResult.getFieldErrors().forEach(fieldError -> message
                .append(fieldError.getField())
                .append(": ")
                .append(fieldError.getDefaultMessage())
                .append(";"));
        return message.toString();
    }
}
