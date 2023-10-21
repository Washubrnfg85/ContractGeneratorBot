package com.archpj.getatestbot.models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "quiz_results")
public class QuizResult {

    @Id
    @GeneratedValue
    private int id;
    private long employeeId;
    private String employeeName;
    private String topic;
    private String results;
    private Timestamp timestamp;

    private List<Integer> questionIds = new ArrayList<>();

    public QuizResult() {}

    public QuizResult(long employeeId, String employeeName, String topic, String results, Timestamp timestamp) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.topic = topic;
        this.results = results;
        this.timestamp = timestamp;
    }

    public void appendQuestionId(int id) {
        questionIds.add(id);
    }

    public void saveQuestionsIds(Session session) {
        for (QuizQuestion quizQuestion : session.getQuizQuestions()) {
            appendQuestionId(quizQuestion.getQuestionId());
        }
    }

    public String calculateScore() {
        String[] array = results.split("\n");
        String correctAnswers = array[0];
        String employeeAnswers = array[1];

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
                score = "Неизвестная ошибка.";
            }
        }
        return score;
    }

    public String trimTimestamp() {
        if (timestamp != null) {
            return new SimpleDateFormat("dd.MM.yyyy  hh:mm").format(timestamp);
        }
        return "";
    }
}
