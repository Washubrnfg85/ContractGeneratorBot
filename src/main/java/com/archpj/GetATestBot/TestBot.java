package com.archpj.GetATestBot;

import com.archpj.GetATestBot.components.Buttons;
import com.archpj.GetATestBot.components.MenuOfSpecs;
import com.archpj.GetATestBot.components.CommonSpecialisations;
import com.archpj.GetATestBot.config.BotConfig;
import com.archpj.GetATestBot.models.QuizResult;
import com.archpj.GetATestBot.models.Quiz;
import com.archpj.GetATestBot.services.QuizResultsService;
import com.archpj.GetATestBot.services.QuizService;
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
    private String specialisation;
    private Quiz quiz = null;
    private QuizResult quizResult;
    private int iterationThroughTest = 0;
    private String employeeAnswers = "";

    @Autowired
    private QuizService quizService;
    @Autowired
    private QuizResultsService quizResultsService;


    public TestBot (BotConfig botConfig) {  // свериться с документацие (нужен ли при этом botConfig и @Configuration
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
    }  // depricated????


    public void onUpdateReceived(Update update) {   // возможно использовать базу активней, напрашивается паттерн State
        long adminTelegramId = botConfig.getAdminTelegramId();

        if (update.hasMessage()) {

            Message incomingMessage = update.getMessage();
            long chatId = update.getMessage().getChatId();
            SendMessage message;

            if (incomingMessage.getText().equals("/start")) {
                iterationThroughTest = 0;
                employeeAnswers = "";

                message = SendMessage.builder().
                        chatId(chatId).
                        text("Желаете начать тест?").
                        replyMarkup(Buttons.suggestTest()).
                        build();

            } else if (incomingMessage.getText().equals("/test")) {
                iterationThroughTest = 0;
                employeeAnswers = "";

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
            } else if (CommonSpecialisations.toList().contains(incomingMessage.getText())) {

                specialisation = incomingMessage.getText();
                iterationThroughTest = 0;
                employeeAnswers = "";

                quiz = new Quiz(quizService, specialisation);

                message = SendMessage.builder().
                        chatId(chatId).
                        text(quiz.getQuestions().get(iterationThroughTest)).
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

                message = SendMessage.builder().
                        chatId(chatId).
                        text("Выбирайте тему:").
                        replyMarkup(MenuOfSpecs.sendMenu()).
                        build();

            } else if (callbackQuery.getData().equals("A") ||
                    callbackQuery.getData().equals("B") ||
                    callbackQuery.getData().equals("C") ||
                    callbackQuery.getData().equals("D")) {

                employeeAnswers += callbackQuery.getData();
                if (iterationThroughTest < quiz.getQuestions().size()) {

                    message = SendMessage.builder().
                            chatId(chatId).
                            text(quiz.getQuestions().get(iterationThroughTest)).
                            replyMarkup(Buttons.suggestAnswers()).
                            build();
                    iterationThroughTest++;

                } else {
                    quiz.setEmployeeAnswers(employeeAnswers);
                    iterationThroughTest = 0;
                    employeeAnswers = "";

                    quizResult = new QuizResult(employeeTelegramId,
                            employeeName,
                            quiz.getSpecialisation(),
                            quiz.getCorrectAnswer() + "\n" + quiz.getEmployeeAnswers(),
                            new Timestamp(System.currentTimeMillis()));
                    quizResult.calculateEmployeeScore();
                    quizResultsService.saveQuizResult(quizResult);

//                    На данный момент востребованность данного функционала под вопросом
//                    if (quizResultsService.checkIfPresents(quizResult)) {
//                        quizResultsService.updateQuizResult(quizResult);
//                    } else {
//                        quizResultsService.saveQuizResult(quizResult);
//                    }

                    message = SendMessage.builder().
                            chatId(chatId).
                            text("Тест по теме " + specialisation + " завершен.\n" +
                                    "Ваш результат: " + quizResult.getScore() +
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
                text(employeeName + " прошел тест по теме " + specialisation + "\n" +
                        "с результатом " + quizResult.getScore()).
                build();

        try {
            execute(messageToAdmin);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
