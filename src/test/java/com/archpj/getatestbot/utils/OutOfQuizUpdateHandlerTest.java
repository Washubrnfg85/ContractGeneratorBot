package com.archpj.getatestbot.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;

class OutOfQuizUpdateHandlerTest {

    private Update update;
    private String errorText = """
                        Обработка такого типа сообщений не предусмотрена функционалом.
                        Воспользуйтесь меню.""";

    @BeforeEach
    void setup() {
        update = Mockito.mock(Update.class);
    }


    @Test
    void shouldReturnErrorTextIfNeitherMessageNorCallback() {
        Mockito.when(!update.hasMessage() && !update.hasCallbackQuery()).thenReturn(true);
}
}
