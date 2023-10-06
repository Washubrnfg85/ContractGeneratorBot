package com.archpj.getatestbot.utils;

import com.archpj.getatestbot.components.BotCommands;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.*;

class OutOfQuizUpdateHandlerTest {

    private Update update;
    private User user = new User();
    private ChatMemberUpdated chatMemberUpdated = new ChatMemberUpdated();
    private Chat chat = new Chat();
    private Message incommingMessage = new Message();

    @BeforeEach
    void setup() {
        update = Mockito.mock(Update.class);
    }

    @Test
    void shouldReturnErrorTextIfNeitherMessageNorCallback() {
        chatMemberUpdated = Mockito.spy(ChatMemberUpdated.class);
        chat = Mockito.spy(Chat.class);

        Mockito.when(update.hasMessage()).thenReturn(false);
        Mockito.when(update.hasCallbackQuery()).thenReturn(false);
        Mockito.when(update.getMyChatMember()).thenReturn(chatMemberUpdated);
        Mockito.when(update.getMyChatMember().getChat()).thenReturn(chat);
        Mockito.when(update.getMyChatMember().getChat().getId()).thenReturn(0L);

        Assertions.assertEquals(OutOfQuizUpdateHandler.handleUpdate(update).getText(), BotCommands.ERROR_TEXT);
    }

    @Test
    void shouldReturnErrorTextIfWrongMessageReceived() {
        incommingMessage = Mockito.spy(Message.class);
        user = Mockito.spy(User.class);

        Mockito.when(update.getMessage()).thenReturn(incommingMessage);
        Mockito.when(update.getMessage().getFrom()).thenReturn(user);
        Mockito.when(update.getMessage().getFrom().getId()).thenReturn(0L);
        Mockito.when(update.getMessage().getText()).thenReturn("");

        Assertions.assertEquals(OutOfQuizUpdateHandler.handleMessage(update).getText(), BotCommands.ERROR_TEXT);
    }
}
