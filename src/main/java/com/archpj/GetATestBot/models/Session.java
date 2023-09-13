package com.archpj.GetATestBot.models;

import java.util.List;

public class Session {
    private long employeeId;
    private String employeeName;
    private List<String> questions;
    private String correctAnswers;
    private String employeeAnswers;

    public Session(long employeeId, String employeeName, List<String> questions, String correctAnswers) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.questions = questions;
        this.correctAnswers = correctAnswers;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
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
}
