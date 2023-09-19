package com.archpj.GetATestBot;

import com.archpj.GetATestBot.config.BotConfig;
import com.archpj.GetATestBot.database.QuizRepository;
import com.archpj.GetATestBot.models.Session;
import com.archpj.GetATestBot.services.SessionService;
import com.archpj.GetATestBot.utils.UpdateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

import static com.archpj.GetATestBot.components.BotCommands.COMMANDS;

@Component
@Slf4j
public class TestBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;

    @Autowired
    private SessionService sessionService;
    @Autowired
    private QuizRepository quizRepository;

    public static Map<Long, Session> sessions;

    public TestBot(BotConfig botConfig) {
        this.botConfig = botConfig;
        this.sessions = new HashMap<>();
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


    public void onUpdateReceived(Update update) {

        Session sessionToUpdate;
        SendMessage sendMessage;
        long employeeId = update.getMessage().getFrom().getId();
        String employeeName = update.getMessage().getFrom().getLastName() != null ?
                update.getMessage().getFrom().getFirstName() + " " + update.getMessage().getFrom().getLastName() :
                update.getMessage().getFrom().getFirstName();


        if (update.hasMessage() && UpdateHandler.updateContainsTopic(update) && !sessions.containsKey(employeeId)) {
            String topic = update.getMessage().getText();
            sessionToUpdate = new Session(employeeId, employeeName, topic);
            sendMessage = sessionToUpdate.sendNextQuestion();
        } else if (update.hasMessage() && !sessions.containsKey(employeeId)) {
            sendMessage = UpdateHandler.handleMessage(update);
        } else if (update.hasCallbackQuery()) {
            sessionToUpdate = passUpdateToSession(employeeId, update);
            sendMessage = sessionToUpdate.sendNextQuestion();
        } else {
            sendMessage = UpdateHandler.handleUpdate(update);
        }

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException();
        }

    }

    public Session passUpdateToSession(long employeeId, Update update) {
        Session session = sessions.get(employeeId);
        session.setUpdate(update);
        return session;
    }

}








//    public void sendResultToAdmin(Long adminTelegramId, String employeeName) {
//        SendMessage messageToAdmin= SendMessage.builder().
//                chatId(adminTelegramId).
//                text(employeeName + " прошел тест по теме " + topic + "\n" +
//                        "с результатом " + quizResult.getScore()).
//                build();
//
//        try {
//            execute(messageToAdmin);
//        } catch (TelegramApiException e) {
//            throw new RuntimeException(e);
//        }
//    }
