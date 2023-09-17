package com.archpj.GetATestBot.models;

import com.archpj.GetATestBot.components.Buttons;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public class Session {

    private Update update;

    private final long employeeId;
    private final String employeeName;
    private List<String> questions;
    private String correctAnswers;
    private String employeeAnswers = "";

    private boolean testInProgress = false;
    private SendMessage message;

    public Session(long employeeId, String employeeName, Update update) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.update = update;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public String getCorrectAnswers() {
        return correctAnswers;
    }

    public String getEmployeeAnswers() {
        return employeeAnswers;
    }

    public void setUpdate(Update update) {

        this.update = update;
    }

    public SendMessage handleUpdate() {

        if (update.hasMessage()) return handleMessage();
        if (update.hasCallbackQuery()) return handleCallbackQuery();

        return SendMessage.builder().
                chatId(update.getMyChatMember().getChat().getId()).
                text("""
                        Обработка такого типа сообщений не предусмотрена функционалом.
                        Воспользуйтесь меню выбора команд.""").
                replyMarkup(Buttons.suggestTest()).
                build();
    }

    private SendMessage handleMessage() {
        Message incomingMessage = update.getMessage();
        long chatId = update.getMessage().getChatId();
        SendMessage message;

        if (testInProgress) {
            message = SendMessage.builder().
                    chatId(chatId).
                    text("""
                            Вы в процессе тестирования.
                            Ответы принимаются только нажатием на одну из кнопок выбора ответа.
                            Если Вы хотите прервать тест, то нажмите кнопку \"Отказаться\".
                            Имейте в этом случае результаты не сохранятся и тест нужно будет пройти заново.""").
                    replyMarkup(Buttons.rejectTest()).
                    build();
        } else {
            message = SendMessage.builder().
                    chatId(chatId).
                    text("""
                            Вы в процессе тестирования.
                            Ответы принимаются только нажатием на одну из кнопок выбора ответа.
                            Если Вы хотите прервать тест, то нажмите кнопку \"Отказаться\".
                            Имейте в этом случае результаты не сохранятся и тест нужно будет пройти заново.""").
                    replyMarkup(Buttons.rejectTest()).
                    build();
        }


        return message;

    }

    private SendMessage handleCallbackQuery() {

        CallbackQuery callbackQuery = update.getCallbackQuery();

        long employeeTelegramId = callbackQuery.getFrom().getId();
        long chatId = callbackQuery.getMessage().getChatId();

        SendMessage message;

        message = SendMessage.builder().
                chatId(chatId).
                text("Ок.\nВы можете начать тест позднее выбрав пункт меню \"/test\"").
                build();

        return message;
    }

    
}
