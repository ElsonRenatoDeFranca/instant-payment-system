package com.example.instantpaymentsystem.businessrule.exception;

import com.example.instantpaymentsystem.businessrule.exception.entities.ErrorData;
import lombok.Getter;
import org.springframework.util.Assert;

import java.util.Set;

@Getter
public class InputDataException extends RuntimeException {

    private final Set<ErrorData> errors;

    public InputDataException(final Set<ErrorData> errors) {
        this(null, null, errors);
    }

    public InputDataException(final String message, final Throwable cause, final Set<ErrorData> errors) {

        super(message, cause);
        Assert.notNull(errors, "Errors List must not be null");
        this.errors = errors;
    }

}