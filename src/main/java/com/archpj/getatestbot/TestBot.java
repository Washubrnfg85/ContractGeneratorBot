package com.archpj.getatestbot;

import com.archpj.getatestbot.config.BotConfig;
import com.archpj.getatestbot.services.SessionManager;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.archpj.getatestbot.components.BotCommands.COMMANDS;

@Component
@Slf4j
public class TestBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;

    @Autowired
    private SessionManager sessionManager;

    public TestBot(BotConfig botConfig) {
        this.botConfig = botConfig;
        try {
            this.execute(new SetMyCommands(COMMANDS, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    public String getBotUsername() {
        return botConfig.getName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }  // depricated????


    @SneakyThrows
    public void onUpdateReceived(Update update) {
        User user = update.hasMessage() ? update.getMessage().getFrom() : update.getCallbackQuery().getFrom();
        long employeeId = user.getId();
        String employeeName = extractEmployeeName(user);

        SendMessage sendMessage;
        sendMessage = sessionManager.processUpdate(update, employeeId, employeeName);

        if (update.hasCallbackQuery()) {
            AnswerCallbackQuery close = AnswerCallbackQuery.builder()
                    .callbackQueryId(update.getCallbackQuery().getId()).
                    build();
            execute(close);
        }
        execute(sendMessage);

        if (sessionManager.sessionIsOver(employeeId)) execute(sessionManager.sendResultToAdmin(employeeId));
        sessionManager.removeSessionIfComplete(employeeId);
    }

    public String extractEmployeeName(User user) {
        return user.getLastName() == null ? user.getFirstName() : user.getFirstName() + " " + user.getLastName();
    }
}