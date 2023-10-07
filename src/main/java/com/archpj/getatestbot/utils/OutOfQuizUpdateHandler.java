package com.archpj.getatestbot.utils;

import com.archpj.getatestbot.components.BotCommands;
import com.archpj.getatestbot.components.MenuOfTopics;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class OutOfQuizUpdateHandler extends UpdateHandler {

    static SendMessage handleMessage(Update update) {
        Message incomingMessage = update.getMessage();

        long employeeId = incomingMessage.getFrom().getId();
        String incomingText = incomingMessage.getText();

        switch (incomingText) {
            case "/start" -> {
                return SendMessage.builder().
                        chatId(employeeId).
                        text("Выбирайте тему").
                        replyMarkup(MenuOfTopics.generateMenu()).
                        build();
            }
            case "/help" -> {
                return SendMessage.builder().
                        chatId(employeeId).
                        text(BotCommands.HELP_TEXT).
                        build();
            }
            default -> {
                return SendMessage.builder().
                        chatId(employeeId).
                        text(BotCommands.USER_ERROR_TEXT).
                        build();
            }
        }
    }

    static SendMessage handleCallbackQuery(Update update) {
        long employeeId = update.getCallbackQuery().getFrom().getId();

        return SendMessage.builder().
                chatId(employeeId).
                text(BotCommands.ERROR_TEXT).
                build();
    }
}
