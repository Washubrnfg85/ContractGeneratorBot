package com.archpj.getatestbot.services;

import com.archpj.getatestbot.components.BotCommands;
import com.archpj.getatestbot.database.QuizQuestionRepository;
import com.archpj.getatestbot.database.QuizResultsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

class LogicProcessorTest {

    private QuizQuestionRepository quizQuestionRepository;
    private QuizResultsRepository quizResultsRepository;

    private final Update update = Mockito.spy(Update.class);
    private final Message incommingMessage = Mockito.spy(Message.class);
    private final User user = Mockito.spy(User.class);
    private final CallbackQuery callbackQuery = Mockito.spy(CallbackQuery.class);

    LogicProcessor logicProcessor = new LogicProcessor(new RepoDataService(quizQuestionRepository, quizResultsRepository));

    @Test
    void testProcessUpdateOutSessionCallback() {
        Mockito.when(update.hasCallbackQuery()).thenReturn(true);
        Mockito.when(update.getCallbackQuery()).thenReturn(callbackQuery);
        Mockito.when(callbackQuery.getFrom()).thenReturn(user);
        Mockito.when(user.getId()).thenReturn(1L);

        SendMessage sendMessage = logicProcessor.processUpdate(update, 1L, "Name");
        Assertions.assertEquals(sendMessage.getText(), BotCommands.ERROR_TEXT);
    }

    @Test
    void testProcessUpdateOutSessionMessageStart() {
        Mockito.when(update.hasMessage()).thenReturn(true);
        Mockito.when(update.getMessage()).thenReturn(incommingMessage);
        Mockito.when(incommingMessage.getFrom()).thenReturn(user);
        Mockito.when(user.getId()).thenReturn(1L);
        Mockito.when(incommingMessage.getText()).thenReturn("/start");

        SendMessage sendMessage = logicProcessor.processUpdate(update, 1L, "Name");
        Assertions.assertEquals(sendMessage.getText(), "Выбирайте тему");
    }

    @Test
    void testProcessUpdateOutSessionMessageHelp() {
        Mockito.when(update.hasMessage()).thenReturn(true);
        Mockito.when(update.getMessage()).thenReturn(incommingMessage);
        Mockito.when(incommingMessage.getFrom()).thenReturn(user);
        Mockito.when(user.getId()).thenReturn(1L);
        Mockito.when(incommingMessage.getText()).thenReturn("/help");

        SendMessage sendMessage = logicProcessor.processUpdate(update, 1L, "Name");
        Assertions.assertEquals(sendMessage.getText(), BotCommands.HELP_TEXT);
    }

    @Test
    void testProcessUpdateOnSessionCallback() {
        Mockito.when(update.hasCallbackQuery()).thenReturn(true);
        Mockito.when(update.getCallbackQuery()).thenReturn(callbackQuery);
        Mockito.when(callbackQuery.getFrom()).thenReturn(user);
        Mockito.when(user.getId()).thenReturn(1L);
        Mockito.when(callbackQuery.getData()).thenReturn("Отказаться");

        SendMessage sendMessage = logicProcessor.processUpdate(update, 1L, "Name");
        Assertions.assertEquals(sendMessage.getText(), "/reject");
    }

}