package com.example.instantpaymentsystem.businessrule.exception;

import com.example.instantpaymentsystem.businessrule.exception.entities.ErrorCode;
import com.example.instantpaymentsystem.businessrule.exception.entities.ErrorData;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

import java.util.Set;

@Getter
@ToString
public class ValidationException extends RuntimeException {

    private final Set<ErrorData> errors;

    public ValidationException(final Set<ErrorData> errors) {
        this(null, null, errors);
    }

    public ValidationException(final String message, final Throwable cause, final Set<ErrorData> errors) {
        super(message, cause);
        Assert.notNull(errors, "Errors List must not be null");
        errors.forEach(error -> {
            error.setTitle(ErrorCode.INVALID_PARAMETER.name().concat(StringUtils.SPACE).concat(error.getTitle()));
            error.setCode(HttpStatus.BAD_REQUEST.toString());
        });
        this.errors = errors;
    }

}
