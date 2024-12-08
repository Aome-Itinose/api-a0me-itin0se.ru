package org.aome.cvapi.util.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Slf4j
@Component
public class ExceptionMessageCollector {

    public String collectMessage(BindingResult bindingResult) {
        StringBuffer message = new StringBuffer();
        bindingResult.getFieldErrors().forEach(fieldError -> message
                .append(fieldError.getField())
                .append(": ")
                .append(fieldError.getDefaultMessage())
                .append(";"));
        log.debug(message.toString());
        return message.toString();
    }
}
