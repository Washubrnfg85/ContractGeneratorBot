package com.archpj.getatestbot.utils;

import com.archpj.getatestbot.components.BotCommands;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.*;

class OutOfQuizUpdateHandlerTest {

    private final Update update = Mockito.spy(Update.class);
    private final User user = Mockito.spy(User.class);
    private final ChatMemberUpdated chatMemberUpdated = Mockito.spy(ChatMemberUpdated.class);
    private final Chat chat = Mockito.spy(Chat.class);
    private final Message message = Mockito.spy(Message.class);
    private final CallbackQuery callbackQuery = Mockito.spy(CallbackQuery.class);


    @Test
    void shouldReturnErrorTextIfNeitherMessageNorCallback() {
        Mockito.when(update.hasMessage()).thenReturn(false);
        Mockito.when(update.hasCallbackQuery()).thenReturn(false);
        Mockito.when(update.getMyChatMember()).thenReturn(chatMemberUpdated);
        Mockito.when(update.getMyChatMember().getChat()).thenReturn(chat);
        Mockito.when(update.getMyChatMember().getChat().getId()).thenReturn(0L);
        Assertions.assertEquals(OutOfQuizUpdateHandler.handleUpdate(update).getText(), BotCommands.USER_ERROR_TEXT);
    }

    @Test
    void shouldReturnErrorTextIfWrongMessageReceived() {
        Mockito.when(update.hasMessage()).thenReturn(true);
        Mockito.when(update.hasCallbackQuery()).thenReturn(false);
        Mockito.when(update.getMessage()).thenReturn(message);
        Mockito.when(update.getMessage().getFrom()).thenReturn(user);
        Mockito.when(update.getMessage().getFrom().getId()).thenReturn(0L);
        Mockito.when(update.getMessage().getText()).thenReturn("");

        Assertions.assertEquals(OutOfQuizUpdateHandler.handleUpdate(update).getText(), BotCommands.USER_ERROR_TEXT);
    }

    @Test
    void shouldReturnErrorTextIfCallbackReceived() {
        Mockito.when(update.hasMessage()).thenReturn(false);
        Mockito.when(update.hasCallbackQuery()).thenReturn(true);
        Mockito.when(update.getCallbackQuery()).thenReturn(callbackQuery);
        Mockito.when(update.getCallbackQuery().getFrom()).thenReturn(user);
        Mockito.when(update.getCallbackQuery().getFrom().getId()).thenReturn(0L);
        Mockito.when(update.hasCallbackQuery()).thenReturn(true);

        Assertions.assertEquals(OutOfQuizUpdateHandler.handleUpdate(update).getText(), BotCommands.ERROR_TEXT);
    }
}
