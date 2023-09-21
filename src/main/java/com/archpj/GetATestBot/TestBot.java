package com.archpj.GetATestBot;

import com.archpj.GetATestBot.components.Buttons;
import com.archpj.GetATestBot.components.CommonTopics;
import com.archpj.GetATestBot.config.BotConfig;
import com.archpj.GetATestBot.database.QuizQuestionRepository;
import com.archpj.GetATestBot.models.Session;
import com.archpj.GetATestBot.services.SessionService;
import com.archpj.GetATestBot.utils.OnQuizUpdateHandler;
import com.archpj.GetATestBot.utils.OutOfQuizUpdateHandler;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.archpj.GetATestBot.components.BotCommands.COMMANDS;

@Component
@Slf4j
public class TestBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;

    @Autowired
    private SessionService sessionService;
    @Autowired
    private QuizQuestionRepository quizQuestionRepository;

    private static Map<Long, Session> sessions;

    public TestBot(BotConfig botConfig) {
        this.botConfig = botConfig;
        sessions = new HashMap<>();
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
        long employeeId = update.hasMessage() ?
                update.getMessage().getFrom().getId() :
                update.getCallbackQuery().getFrom().getId();
        String employeeName = update.hasMessage() ?
                update.getMessage().getFrom().getLastName() != null ?
                        update.getMessage().getFrom().getFirstName() + " " + update.getMessage().getFrom().getLastName() :
                        update.getMessage().getFrom().getFirstName() :
                update.getCallbackQuery().getFrom().getLastName() != null ?
                        update.getCallbackQuery().getFrom().getFirstName() + " " + update.getCallbackQuery().getFrom().getLastName() :
                        update.getCallbackQuery().getFrom().getFirstName();

        if (employeeHasSession(employeeId)) {
            System.out.println("if 1");
            sendMessage = OnQuizUpdateHandler.handleUpdate(update);
        } else {
            System.out.println("else 1");
            sendMessage = OutOfQuizUpdateHandler.handleUpdate(update);
        }

        if (sendMessage.getText().equals("[A-D]") || CommonTopics.containsValue(sendMessage.getText())) {
            System.out.println("if 2");
            if (!employeeHasSession(employeeId)) {
                sessions.put(employeeId, new Session(employeeId,
                        employeeName,
                        sendMessage.getText()));
            }
            sendMessage = sessions.get(employeeId).sendNextQuestion();
        }

        if (update.hasCallbackQuery()) {
            AnswerCallbackQuery close = AnswerCallbackQuery.builder()
                    .callbackQueryId(update.getCallbackQuery().getId()).build();
            execute(close);
        }

        execute(sendMessage);

    }

    public boolean employeeHasSession(long employeeId) {
        return sessions.containsKey(employeeId);
    }

}