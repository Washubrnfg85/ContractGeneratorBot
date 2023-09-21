package com.archpj.GetATestBot.models;

import com.archpj.GetATestBot.components.Buttons;
import com.archpj.GetATestBot.services.SessionService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.sql.Timestamp;
import java.util.List;

public class Session {

    private final long employeeId;
    private final String employeeName;
    private final String topic;

    private List<QuizQuestion> quizQuestions;
    private String correctAnswers;
    private String employeeAnswers = "";
    private int iterationsThroughTest;

    private Update update;
    private boolean testInProgress = false;
    private Timestamp timestamp;


    public Session(long employeeId, String employeeName, String topic) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.topic = topic;

        loadQuizQuestions();
        this.testInProgress = true;
        this.iterationsThroughTest = 0;

        System.out.println("Session created");
    }

    public void loadQuizQuestions() {
        quizQuestions = SessionService.loadQuizQuestions(topic);
        initializeCorrectAnswers();
    }

    public void initializeCorrectAnswers() {
        StringBuilder result = new StringBuilder();
        for (QuizQuestion question : quizQuestions) {
            result.append(question.getCorrectAnswer());
        }
        correctAnswers = result.toString();
    }

    public SendMessage sendNextQuestion() {
        if (iterationsThroughTest < quizQuestions.size()) {
            SendMessage message = SendMessage.builder().
                    chatId(employeeId).
                    text(quizQuestions.get(iterationsThroughTest).getQuestion()).
                    replyMarkup(Buttons.suggestAnswers()).
                    build();
            iterationsThroughTest++;
            return message;
        }
        return completeQuiz();
    }

    public SendMessage completeQuiz() {
        resetIterationsThroughTest();
        setTimestamp(new Timestamp(System.currentTimeMillis()));
        SessionService.saveQuizResult(new QuizResult(employeeId, employeeName, topic,
                correctAnswers + " " + employeeAnswers, timestamp));
        return SendMessage.builder().
                chatId(employeeId).
                text("Тест по теме " + topic + " завершен.\n" +
                        "Ваш результат: " + calculateScore() +
                        "\nВы можете выбрать другую тему или пересдать эту.").
                build();
    }

    public String calculateScore() {
        String score = "";

        if (correctAnswers.equals(employeeAnswers)) {
            score = employeeAnswers.length() + "/" + correctAnswers.length();
        } else {
            String[] correctLetters = correctAnswers.split("");
            String[] employeeLetters = employeeAnswers.split("");

            if (correctLetters.length == employeeLetters.length) {
                int numberOfScores = 0;
                for (int i = 0; i < correctLetters.length; i++) {
                    if (correctLetters[i].equals(employeeLetters[i])) numberOfScores++;
                }
                score = numberOfScores + "/" + correctAnswers.length();
            } else {
                score = "Неизвестная ошибка. Необходимо пересдать тест и сообщить разработчику";
                //Logging
            }
        }
        return score;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void resetIterationsThroughTest() {
        this.iterationsThroughTest = 0;
    }

//    public void sendResultToAdmin(Long adminTelegramId, String employeeName) {
//        SendMessage messageToAdmin= SendMessage.builder().
//                chatId(adminTelegramId).
//                text(employeeName + " прошел тест по теме " + topic + "\n" +
//                        "с результатом " + calculateScore()).
//                build();
//    }

    public void setUpdate(Update update) {
        this.update = update;
    }

    public boolean isTestInProgress() {
        return testInProgress;
    }

    public String getTopic() {
        return topic;
    }
}
