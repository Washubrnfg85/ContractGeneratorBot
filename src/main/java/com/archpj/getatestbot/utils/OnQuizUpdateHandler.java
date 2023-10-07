package com.archpj.getatestbot.utils;

import com.archpj.getatestbot.components.BotCommands;
import com.archpj.getatestbot.components.Buttons;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class OnQuizUpdateHandler extends UpdateHandler {

    static SendMessage handleMessage(Update update) {
        Message incomingMessage = update.getMessage();
        long employeeId = incomingMessage.getFrom().getId();

        return SendMessage.builder().
                chatId(employeeId).
                text("""
                            Вы в процессе тестирования.
                            Ответы принимаются только нажатием на одну из кнопок выбора ответа.
                            Если Вы хотите прервать тест, то нажмите кнопку "Отказаться".
                            Примите во внимание что в этом случае результаты не сохранятся и тест нужно будет пройти заново.""").
                replyMarkup(Buttons.rejectTest()).
                build();
    }

    static SendMessage handleCallbackQuery(Update update) {
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
