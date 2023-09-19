package com.archpj.GetATestBot.models;

import com.archpj.GetATestBot.components.Buttons;
import com.archpj.GetATestBot.services.QuizResultsService;
import com.archpj.GetATestBot.services.SessionService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.sql.Timestamp;
import java.util.List;

public class Session {

    private final long employeeId;
    private final String employeeName;
    private final String topic;

    private List<String> questions;
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
        loadQuestions();
        this.testInProgress = true;
        this.iterationsThroughTest = 0;
    }

    public void loadQuestions() {
        questions = SessionService.loadQuizQuestions(topic);
        correctAnswers = SessionService.loadCorrectAnswers(topic);
    }

    public SendMessage sendNextQuestion() {
        if (iterationsThroughTest < questions.size()) {
            SendMessage message = SendMessage.builder().
                    chatId(employeeId).
                    text(questions.get(iterationsThroughTest)).
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
        QuizResultsService.saveQuizResult(new QuizResult(employeeId, employeeName, topic,
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

    public Update getUpdate() {
        return update;
    }

    public void setUpdate(Update update) {
        this.update = update;
    }

    public boolean isTestInProgress() {
        return testInProgress;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getTopic() {
        return topic;
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

    public int getIterationsThroughTest() {
        return iterationsThroughTest;
    }
}
