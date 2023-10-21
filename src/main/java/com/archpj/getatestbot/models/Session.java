package com.archpj.getatestbot.models;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class Session {

    private final long employeeId;
    private final String employeeName;
    private final String topic;

    private QuizResult quizResult;
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
        this.iterationsThroughTest = 0;
    }

    public void saveQuestionsIds() {
        for (QuizQuestion quizQuestion : quizQuestions) {
            quizResult.appendQuestionId(quizQuestion.getQuestionId());
        }
    }

    public void appendAnswer(String letter) {
        this.employeeAnswers += letter;
    }

    public boolean isOver() {
        return this.isOver;
    }

}
