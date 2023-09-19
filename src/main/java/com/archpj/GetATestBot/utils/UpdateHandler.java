package com.archpj.GetATestBot.utils;

import com.archpj.GetATestBot.components.Buttons;
import com.archpj.GetATestBot.components.CommonTopics;
import com.archpj.GetATestBot.components.MenuOfTopics;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.archpj.GetATestBot.TestBot.sessions;


public class UpdateHandler {

    public static SendMessage handleUpdate(Update update) {

        if (update.hasMessage()) return handleMessage(update);
        if (update.hasCallbackQuery()) return handleCallbackQuery(update);

        return SendMessage.builder().
                chatId(update.getMyChatMember().getChat().getId()).
                text("""
                        Обработка такого типа сообщений не предусмотрена функционалом.
                        Воспользуйтесь меню выбора команд.""").
                replyMarkup(Buttons.suggestTest()).
                build();
    }

    public static SendMessage handleMessage(Update update) {
        long employeeId = update.getMessage().getFrom().getId();
        Message incomingMessage = update.getMessage();
        String incomingText = incomingMessage.getText();
        SendMessage message;

        if (sessions.containsKey(employeeId) && sessions.get(employeeId).isTestInProgress()) {
            message = SendMessage.builder().
                    chatId(employeeId).
                    text("""
                            Вы в процессе тестирования.
                            Ответы принимаются только нажатием на одну из кнопок выбора ответа.
                            Если Вы хотите прервать тест, то нажмите кнопку \"Отказаться\".
                            Примите во внимание что в этом случае результаты не сохранятся и тест нужно будет пройти заново.""").
                    replyMarkup(Buttons.rejectTest()).
                    build();
        } else {
            if (incomingText.equals("/start")) {
                message = SendMessage.builder().
                        chatId(employeeId).
                        text("Выбирайте тему").
                        replyMarkup(MenuOfTopics.generateMenu()).
                        build();
            } else {
                message = SendMessage.builder().
                        chatId(employeeId).
                        text("Заглушка").
                        build();
            }

        }


        return message;

    }

    public static SendMessage handleCallbackQuery(Update update) {

        CallbackQuery callbackQuery = update.getCallbackQuery();

        long employeeTelegramId = callbackQuery.getFrom().getId();
        long chatId = callbackQuery.getMessage().getChatId();

        SendMessage message;

        message = SendMessage.builder().
                chatId(chatId).
                text("Заглушка").
                build();

        return message;
    }

    public static boolean updateContainsTopic(Update update) {
        String topic = update.getMessage().getText();
        return CommonTopics.toList().contains(topic);
    }
}
