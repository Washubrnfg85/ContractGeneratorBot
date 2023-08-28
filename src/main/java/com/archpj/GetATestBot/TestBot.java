package com.archpj.GetATestBot;

import com.archpj.GetATestBot.components.Buttons;
import com.archpj.GetATestBot.components.MenuOfSpecs;
import com.archpj.GetATestBot.config.BotConfig;
import com.archpj.GetATestBot.models.Employee;
import com.archpj.GetATestBot.models.SpecResult;
import com.archpj.GetATestBot.models.SpecTest;
import com.archpj.GetATestBot.services.EmployeeService;
import com.archpj.GetATestBot.services.SpecResultsService;
import com.archpj.GetATestBot.services.SpecTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.Timestamp;

import static com.archpj.GetATestBot.components.BotCommands.COMMANDS;
import static com.archpj.GetATestBot.components.BotCommands.HELP_TEXT;

@Component
@Slf4j
public class TestBot extends TelegramLongPollingBot {

    final BotConfig botConfig;
    private String spec;
    private SpecTest test = null;
    private int iterationThroughTest = 0;
    private String employeeAnswers = "";

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private SpecTestService specTestService;
    @Autowired
    private SpecResultsService specResultsService;


    public TestBot (BotConfig botConfig) {
        this.botConfig = botConfig;
        try {
            this.execute(new SetMyCommands(COMMANDS, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e){
            log.error(e.getMessage());
        }
    }

    public String getBotUsername() {
        return botConfig.getName();
    }

    public String getBotToken() {
        return botConfig.getToken();
    }


    public void onUpdateReceived(Update update) {
        long adminTelegramId = botConfig.getAdminTelegramId();

        if (update.hasMessage()) {

            Message incomingMessage = update.getMessage();
            String employeeName = incomingMessage.getFrom().getFirstName() + " " + incomingMessage.getFrom().getLastName();
            long employeeTelegramId = incomingMessage.getFrom().getId();
            long chatId = update.getMessage().getChatId();
            SendMessage message;

            if (incomingMessage.getText().equals("/start")) {
                iterationThroughTest = 0;
                employeeAnswers = "";

                if (!employeeService.hasEmployee(employeeTelegramId)) {
                    employeeService.save(new Employee(employeeTelegramId, employeeName, new Timestamp(System.currentTimeMillis())));
                }
                message = SendMessage.builder().
                        chatId(chatId).
                        text("Желаете начать тест?").
                        replyMarkup(Buttons.suggestTest()).
                        build();

            } else if (incomingMessage.getText().equals("/test")) {
                iterationThroughTest = 0;
                employeeAnswers = "";

                if (!employeeService.hasEmployee(employeeTelegramId)) {
                    employeeService.save(new Employee(employeeTelegramId, employeeName, new Timestamp(System.currentTimeMillis())));
                }
                message = SendMessage.builder().
                        chatId(chatId).
                        text("Выбирайте тему:").
                        replyMarkup(MenuOfSpecs.sendMenu()).
                        build();

            } else if (incomingMessage.getText().equals("/help")) {
                message = SendMessage.builder().
                        chatId(chatId).
                        text(HELP_TEXT).
                        build();

            } else if (incomingMessage.getText().equals("Имплантология") ||
                    incomingMessage.getText().equals("Хирургия") ||
                    incomingMessage.getText().equals("Терапия") ||
                    incomingMessage.getText().equals("Гигиеническая чистка") ||
                    incomingMessage.getText().equals("Ортопедия") ||
                    incomingMessage.getText().equals("Ортодонтия")) {

                spec = incomingMessage.getText();
                iterationThroughTest = 0;
                employeeAnswers = "";

                test = new SpecTest(specTestService, spec);

                message = SendMessage.builder().
                        chatId(chatId).
                        text(test.getQuestions().get(iterationThroughTest)).
                        replyMarkup(Buttons.suggestAnswers()).
                        build();
                iterationThroughTest++;

            } else {
                message = SendMessage.builder().
                        chatId(chatId).text("""
                                Вы что-то не то делайте.
                                ("Нормально делай - нормально будет!
                                Сократ 429 год до н.э.")
                                Если возникли трудности, нажмите "/help\"""").
                        build();
            }

            try {
                execute(message);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }

        } else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();

            String employeeName = callbackQuery.getFrom().getFirstName() + " " + callbackQuery.getFrom().getLastName();
            long employeeTelegramId = callbackQuery.getFrom().getId();
            long chatId = callbackQuery.getMessage().getChatId();

            SendMessage message;

            if (callbackQuery.getData().equals("/test rejected")) {
                message = SendMessage.builder().
                        chatId(chatId).
                        text("Ок.\nВы можете начать тест позднее выбрав пункт меню \"/test\"").
                        build();
                //Add sending of picture functional

            } else if (callbackQuery.getData().equals("/test")) {
                iterationThroughTest = 0;
                employeeAnswers = "";

                if (!employeeService.hasEmployee(employeeTelegramId)) {
                    employeeService.save(new Employee(employeeTelegramId, employeeName, new Timestamp(System.currentTimeMillis())));
                }
                message = SendMessage.builder().
                        chatId(chatId).
                        text("Выбирайте тему:").
                        replyMarkup(MenuOfSpecs.sendMenu()).
                        build();

            } else if (callbackQuery.getData().equals("A") ||
                    callbackQuery.getData().equals("B") ||
                    callbackQuery.getData().equals("C") ||
                    callbackQuery.getData().equals("D")) {

                if (iterationThroughTest < test.getQuestions().size()) {
                    employeeAnswers += callbackQuery.getData();

                    message = SendMessage.builder().
                            chatId(chatId).
                            text(test.getQuestions().get(iterationThroughTest)).
                            replyMarkup(Buttons.suggestAnswers()).
                            build();
                    iterationThroughTest++;

                } else {
                    employeeAnswers += callbackQuery.getData();
                    test.setEmployeeAnswers(employeeAnswers);
                    iterationThroughTest = 0;
                    employeeAnswers = "";
                    test.calculateEmployeeScore();

                    SpecResult specResult = new SpecResult(employeeTelegramId,
                            test.getSpec(),
                            test.getCorrectAnswer() + "\n" + test.getEmployeeAnswers(),
                            test.getEmployeeScore()
                            );

                    if (specResultsService.checkIfPresents(specResult)) {
                        specResultsService.updateSpecResult(specResult);
                    } else {
                        specResultsService.saveSpecResult(specResult);
                    }

                    message = SendMessage.builder().
                            chatId(chatId).
                            text("Тест по теме " + spec + " завершен.\n" +
                                    "Ваш результат: " + test.getEmployeeScore() +
                                    "\nВы можете выбрать другую тему или пересдать эту.").
                            build();

                    sendResultToAdmin(adminTelegramId, employeeName);

                }
            } else {
                message = SendMessage.builder().
                        chatId(chatId).
                        text("Query hadn't been processed").
                        build();

            }

            AnswerCallbackQuery close = AnswerCallbackQuery.builder()
                    .callbackQueryId(callbackQuery.getId()).build();

            try {
                execute(close);
                execute(message);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void sendResultToAdmin(Long adminTelegramId, String employeeName) {
        SendMessage messageToAdmin= SendMessage.builder().
                chatId(adminTelegramId).
                text(employeeName + " прошел тест по теме " + spec + ".\n" +
                        "с результатом: " + test.getEmployeeScore()).
                build();

        try {
            execute(messageToAdmin);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
