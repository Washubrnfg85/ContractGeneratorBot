package com.archpj.GetATestBot;

import com.archpj.GetATestBot.config.BotConfig;
import com.archpj.GetATestBot.services.SessionManager;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.archpj.GetATestBot.components.BotCommands.COMMANDS;

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

        SendMessage sendMessage;
        long employeeId = extractEmployeeId(update);
        String employeeName = extractEmployeeName(update);

        sendMessage = sessionManager.processUpdate(update, employeeId, employeeName);

        if (update.hasCallbackQuery()) {
            AnswerCallbackQuery close = AnswerCallbackQuery.builder()
                    .callbackQueryId(update.getCallbackQuery().getId()).build();
            execute(close);
        }
        execute(sendMessage);

        sessionManager.removeSessionIfComplete(employeeId);
    }

    public long extractEmployeeId(Update update) {
        return update.hasMessage() ?
                update.getMessage().getFrom().getId() :
                update.getCallbackQuery().getFrom().getId();
    }

    public String extractEmployeeName(Update update) {
        return update.hasMessage() ?
                update.getMessage().getFrom().getLastName() != null ?
                        update.getMessage().getFrom().getFirstName() + " " + update.getMessage().getFrom().getLastName() :
                        update.getMessage().getFrom().getFirstName() :
                update.getCallbackQuery().getFrom().getLastName() != null ?
                        update.getCallbackQuery().getFrom().getFirstName() + " " + update.getCallbackQuery().getFrom().getLastName() :
                        update.getCallbackQuery().getFrom().getFirstName();
    }
}