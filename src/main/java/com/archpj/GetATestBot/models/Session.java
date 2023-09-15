package com.archpj.GetATestBot.models;

import com.archpj.GetATestBot.components.Buttons;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public class Session {

    private Update update;

    private long employeeId;
    private String employeeName;
    private List<String> questions;
    private String correctAnswers;
    private String employeeAnswers;

    private SendMessage message;

    public Session(long employeeId, String employeeName, Update update) {
        System.out.println("Session constructor working");

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

    public SendMessage setUpdate(Update update) {
        System.out.println("setUpdate working");

        this.update = update;
        return handleUpdate();
    }

    public SendMessage handleUpdate() {
        System.out.println("handleUpdate working");

        if (update.hasMessage()) return handleMessage();
        if (update.hasCallbackQuery()) return handleCallbackQuery();

        return SendMessage.builder().
                chatId(update.getMyChatMember().getChat().getId()).
                text("Neither message nor callback.").
                replyMarkup(Buttons.suggestTest()).
                build();
    }

    private SendMessage handleMessage() {
        System.out.println("handleMessage working");

        Message incomingMessage = update.getMessage();
        long chatId = update.getMessage().getChatId();
        SendMessage message;

        message = SendMessage.builder().
                chatId(chatId).
                text("Желаете начать тест?").
                replyMarkup(Buttons.suggestTest()).
                build();

        return message;

    }

    private SendMessage handleCallbackQuery() {
        System.out.println("handleCallbackQuery working");

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
