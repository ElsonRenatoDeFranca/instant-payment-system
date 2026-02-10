package com.example.instantpaymentsystem.businessrule.messages;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageSourceServiceImplTest {

    private static final String VALID_KEY = "VALID_KEY";

    private static final String VALID_MESSAGE = "VALID_MESSAGE";

    @Mock
    private MessageSource mockMessageSource;

    @InjectMocks
    private MessageSourceServiceImpl messageSourceServiceImpl;

    @Test
    @Tag("unit")
    void getMessage_withCorrectMessageKey_shouldBeReturnCorrectMessage() {

        when(mockMessageSource.getMessage(VALID_KEY, null, LocaleContextHolder.getLocale()))
                .thenReturn(VALID_MESSAGE);

        String message = messageSourceServiceImpl.getMessage(VALID_KEY);

        assertThat(message).isNotEmpty().isEqualTo(VALID_MESSAGE);
        verify(mockMessageSource).getMessage(VALID_KEY, null, LocaleContextHolder.getLocale());
    }

    @Test
    @Tag("unit")
    void getMessage_withInvalidKey_shouldBeNull() {

        assertThat(messageSourceServiceImpl.getMessage(" ")).isNull();

        verify(mockMessageSource).getMessage(" ", null, LocaleContextHolder.getLocale());
    }

    @Test
    @Tag("unit")
    void getMessage_withCorrectMessageKeyAndQtdArgs_shouldBeReturnCorrectMessage() {
        final Object[] args = {"abc"};

        when(mockMessageSource.getMessage(VALID_KEY, args, LocaleContextHolder.getLocale()))
                .thenReturn(VALID_MESSAGE);

        String message = messageSourceServiceImpl.getMessage(VALID_KEY, args);

        assertThat(message).isNotEmpty().isEqualTo(VALID_MESSAGE);
        verify(mockMessageSource).getMessage(VALID_KEY, args, LocaleContextHolder.getLocale());
    }

    @Test
    @Tag("unit")
    void getMessage_withCorrectMessageKeyButIncorrectLocale_shouldBeNoSuchMessageException() {
        final Object[] args = {"abc"};

        when(mockMessageSource.getMessage(VALID_KEY, args, LocaleContextHolder.getLocale()))
                .thenThrow(NoSuchMessageException.class);

        assertThrows(NoSuchMessageException.class, () -> messageSourceServiceImpl.getMessage(VALID_KEY, args));

        verify(mockMessageSource).getMessage(VALID_KEY, args, LocaleContextHolder.getLocale());
        verifyNoMoreInteractions(mockMessageSource);
    }
}