package com.archpj.GetATestBot;

import com.archpj.GetATestBot.config.BotConfig;
import com.archpj.GetATestBot.models.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

import static com.archpj.GetATestBot.components.BotCommands.COMMANDS;

@Component
@Slf4j
public class TestBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private List<Session> sessions;

    public TestBot(BotConfig botConfig) {  // свериться с документацие (нужен ли при этом botConfig и @Configuration
        this.botConfig = botConfig;
        this.sessions = new ArrayList<>();
        try {
            this.execute(new SetMyCommands(COMMANDS, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    public String getBotUsername() {
        return botConfig.getName();
    }

    public String getBotToken() {
        return botConfig.getToken();
    }  // depricated????


    public void onUpdateReceived(Update update) {

        Session sessionToUpdate;
        SendMessage sendMessage;
        long employeeId = update.getMessage().getFrom().getId();
        String employeeName = update.getMessage().getFrom().getLastName() != null ?
                update.getMessage().getFrom().getFirstName() + " " + update.getMessage().getFrom().getLastName() :
                update.getMessage().getFrom().getFirstName();

        if (sessions.isEmpty() || !employeeHasSession(employeeId)) {
            System.out.println("Creating new Session");
            sessionToUpdate = new Session(employeeId, employeeName, update);
        } else {
             sessionToUpdate = passUpdateToSession(employeeId, update);
        }

        sendMessage = sessionToUpdate.handleUpdate();

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException();
        }

    }

    public boolean employeeHasSession(long employeeId) {
        for (Session session : sessions) {
            if (session.getEmployeeId() == employeeId) {
                return true;
            }
        }
        return false;
    }

    public Session passUpdateToSession(long employeeId, Update update) {
        Session session = sessions.stream().
                filter(s -> employeeId == s.getEmployeeId()).
                findAny().get();
        session.setUpdate(update);
        return session;
    }
}








//    public void sendResultToAdmin(Long adminTelegramId, String employeeName) {
//        SendMessage messageToAdmin= SendMessage.builder().
//                chatId(adminTelegramId).
//                text(employeeName + " прошел тест по теме " + specialisation + "\n" +
//                        "с результатом " + quizResult.getScore()).
//                build();
//
//        try {
//            execute(messageToAdmin);
//        } catch (TelegramApiException e) {
//            throw new RuntimeException(e);
//        }
//    }
