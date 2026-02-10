package com.example.instantpaymentsystem.businessrule.messages;

public interface MessageSourceService {

    String getMessage(String messageProperties);

    String getMessage(String messageProperties, Object... args);
}
