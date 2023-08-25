package com.archpj.GetATestBot.models;

import com.archpj.GetATestBot.services.SpecTestService;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "spec_test_data")
public class SpecTest {
    @Transient
    private SpecTestService specTestService;

    @Transient
    private List<String> questions;

    @Transient
    private String correctAnswers;

    @Transient
    private String employeeAnswers;
    @Transient
    private String employeeScore;

    @Id
    private int questionId;
    private String spec;
    private String question;
    private String correctAnswer;

    public SpecTest(SpecTestService specTestService, String spec) {
        this.specTestService = specTestService;
        this.spec = spec;

        questions = specTestService.loadSpecQuestions(spec);
        correctAnswer = specTestService.loadCorrectAnswers(spec);
        this.employeeAnswers = "";
    }

    public List<String> getQuestions() {
        return questions;
    }

    public void setEmployeeAnswers(String employeeAnswers) {
        this.employeeAnswers = employeeAnswers;
    }

    public String getEmployeeScore() {
        return employeeScore;
    }

    public void calculateEmployeeScore() {
        if (correctAnswer.equals(employeeAnswers)) {
            employeeScore = employeeAnswers.length() + "/" + correctAnswer.length();
        } else {
            String[] correctLetters = correctAnswer.split("");
            String[] employeeLetters = employeeAnswers.split("");

            if (correctLetters.length == employeeLetters.length) {
                int numberOfScores = 0;
                for (int i = 0; i < correctLetters.length; i++) {
                    if (correctLetters[i].equals(employeeLetters[i])) numberOfScores++;
                }
                employeeScore = numberOfScores + "/" + correctAnswer.length();
            } else {
                employeeScore = "Неизвестная ошибка. Необходимо пересдать тест и сообщить разработчику";
            }
        }
    }
}
