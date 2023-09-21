package com.archpj.GetATestBot.models;

import com.archpj.GetATestBot.components.Buttons;
import com.archpj.GetATestBot.services.SessionServiceImpl;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.sql.Timestamp;
import java.util.List;

public class Session {

    private final long employeeId;
    private final String employeeName;
    private final String topic;

    private List<QuizQuestion> quizQuestions;
    private String correctAnswers;
    private String employeeAnswers = "";
    private Timestamp timestamp;
    private int iterationsThroughTest;
    private boolean isOver = false;


    public Session(long employeeId, String employeeName, String topic) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.topic = topic;

        loadQuizQuestions();  //
        this.iterationsThroughTest = 0;
    }

    public void loadQuizQuestions() {
        quizQuestions = SessionServiceImpl.loadQuizQuestions(topic);
        initializeCorrectAnswers();
    }

    public void initializeCorrectAnswers() {
        StringBuilder result = new StringBuilder();
        for (QuizQuestion question : quizQuestions) {
            result.append(question.getCorrectAnswer());
        }
        correctAnswers = result.toString();
    }

    public void appendAnswer(String letter) {
        this.employeeAnswers += letter;
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
        System.out.println("CompleteQuiz");

        setTimestamp(new Timestamp(System.currentTimeMillis()));
        SessionServiceImpl.saveQuizResult(new QuizResult(employeeId, employeeName, topic,
                correctAnswers + "\n" + employeeAnswers, timestamp));
        this.isOver = true;
        return SendMessage.builder().
                chatId(employeeId).
                text("Тест по теме " + topic + " завершен.\n" +
                        "Ваш результат: " + calculateScore() +
                        "\nВы можете выбрать другую тему или пересдать эту.").
                build();
    }

    public String calculateScore() {
        System.out.println("Calculate Score");

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

    public boolean isOver() {
        return this.isOver;
    }

//    public void sendResultToAdmin(Long adminTelegramId, String employeeName) {
//        SendMessage messageToAdmin= SendMessage.builder().
//                chatId(adminTelegramId).
//                text(employeeName + " прошел тест по теме " + topic + "\n" +
//                        "с результатом " + calculateScore()).
//                build();
//    }
}
