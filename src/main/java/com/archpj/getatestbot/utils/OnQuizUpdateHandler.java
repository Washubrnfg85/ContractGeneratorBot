package com.archpj.getatestbot.utils;

import com.archpj.getatestbot.components.BotCommands;
import com.archpj.getatestbot.components.Buttons;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class OnQuizUpdateHandler {

    public static SendMessage handleUpdate(Update update) {
        if (update.hasMessage()) return handleMessage(update);
        if (update.hasCallbackQuery()) return handleCallbackQuery(update);

        return SendMessage.builder().
                chatId(update.getMyChatMember().getChat().getId()).
                text(BotCommands.USER_ERROR_TEXT).
                build();
    }


    private static SendMessage handleMessage(Update update) {
        Message incomingMessage = update.getMessage();
        long employeeId = incomingMessage.getFrom().getId();

        return SendMessage.builder().
                chatId(employeeId).
                text(BotCommands.ON_QUIZ_ERROR_TEXT).
                replyMarkup(Buttons.rejectTest()).
                build();
    }

    private static SendMessage handleCallbackQuery(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        long employeeId = callbackQuery.getFrom().getId();

        switch (callbackQuery.getData()) {
            case "Отказаться" -> {
                return SendMessage.builder().
                        chatId(employeeId).
                        text("/reject").
                        build();
            }
            case "A", "B", "C", "D" -> {
                return SendMessage.builder().
                        chatId(employeeId).
                        text(callbackQuery.getData()).
                        build();
            }
            default -> {
                return SendMessage.builder().
                            chatId(employeeId).
                            text(BotCommands.ERROR_TEXT).
                            build();
            }
        }
    }

}
