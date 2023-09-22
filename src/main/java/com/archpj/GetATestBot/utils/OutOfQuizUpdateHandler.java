package com.archpj.GetATestBot.utils;

import com.archpj.GetATestBot.components.BotCommands;
import com.archpj.GetATestBot.components.MenuOfTopics;
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
        SendMessage message;

        switch (incomingText) {
            case "/start" -> message = SendMessage.builder().
                    chatId(employeeId).
                    text("Выбирайте тему").
                    replyMarkup(MenuOfTopics.generateMenu()).
                    build();
            case "/help" -> message = SendMessage.builder().
                    chatId(employeeId).
                    text(BotCommands.HELP_TEXT).
                    build();
            default -> message = SendMessage.builder().
                    chatId(employeeId).
                    text(incomingText).
                    build();
        }
        return message;
    }

    private static SendMessage handleCallbackQuery(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        long employeeId = callbackQuery.getFrom().getId();
        SendMessage message;

        message = SendMessage.builder().
                chatId(employeeId).
                text("Если Вы читаете это сообщение, то что-то пошло не так. Обратитесь к разработчику").
                build();

        return message;
    }
}
