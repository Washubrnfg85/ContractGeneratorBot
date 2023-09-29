package com.archpj.getatestbot.utils;

import com.archpj.getatestbot.components.BotCommands;
import com.archpj.getatestbot.components.MenuOfTopics;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;


public class OutOfQuizUpdateHandler {

    public static SendMessage handleUpdate(Update update) {
        if (update.hasMessage()) return handleMessage(update);
        if (update.hasCallbackQuery()) return handleCallbackQuery(update);

        return SendMessage.builder().
                chatId(update.getMyChatMember().getChat().getId()).
                text("""
                        Обработка такого типа сообщений не предусмотрена функционалом.
                        Воспользуйтесь меню.""").
                build();
    }


    public static SendMessage handleMessage(Update update) {
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
                        text(incomingText).
                        build();
            }
        }
    }

    private static SendMessage handleCallbackQuery(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        long employeeId = callbackQuery.getFrom().getId();

        return SendMessage.builder().
                chatId(employeeId).
                text("Если Вы читаете это сообщение, то что-то пошло не так. Обратитесь к разработчику").
                build();
    }
}
