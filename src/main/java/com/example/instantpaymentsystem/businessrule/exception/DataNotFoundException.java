package com.example.instantpaymentsystem.businessrule.exception;

import com.example.instantpaymentsystem.businessrule.exception.entities.ErrorData;
import lombok.Getter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@ToString
public class DataNotFoundException extends RuntimeException {

    private final Set<ErrorData> errors;

    public DataNotFoundException(final String title, final String detail) {
        this.errors = new HashSet<>();
        this.errors.add(new ErrorData(title, detail));
    }

}
