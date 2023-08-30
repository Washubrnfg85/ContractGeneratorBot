package com.archpj.GetATestBot.models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "quiz_results")
public class QuizResult {

    @Id
    @GeneratedValue
    private int id;

    private long telegramId;

    private String employeeName;

    private String specialisation;

    private String results;

    private Timestamp timestamp;

    @Transient
    private String score;


    public QuizResult() {}

    public QuizResult(long telegramId, String employeeName, String specialisation, String results, Timestamp timestamp) {
        this.telegramId = telegramId;
        this.employeeName = employeeName;
        this.specialisation = specialisation;
        this.results = results;
        this.timestamp = timestamp;
    }


    public String getSpecialisation() {
        return specialisation;
    }

    public void calculateEmployeeScore() {
        String[] correctAndEmployeeResults = this.results.split("\n");
        String correctAnswer = correctAndEmployeeResults[0];
        String employeeAnswers = correctAndEmployeeResults[1];

        if (correctAnswer.equals(employeeAnswers)) {
            score = employeeAnswers.length() + "/" + correctAnswer.length();
        } else {
            String[] correctLetters = correctAnswer.split("");
            String[] employeeLetters = employeeAnswers.split("");

            if (correctLetters.length == employeeLetters.length) {
                int numberOfScores = 0;
                for (int i = 0; i < correctLetters.length; i++) {
                    if (correctLetters[i].equals(employeeLetters[i])) numberOfScores++;
                }
                score = numberOfScores + "/" + correctAnswer.length();
            } else {
                score = "Неизвестная ошибка. Необходимо пересдать тест и сообщить разработчику";
            }
        }
    }

//    Method hasn't been tested yet.
//
//    public String getDetailedResult() {
//
//        String[] correctAndEmployeeResults = this.results.split("\n");
//        String correctAnswer = correctAndEmployeeResults[0];
//        String employeeAnswers = correctAndEmployeeResults[1];
//
//        List<String> derailedResults = new ArrayList<>(correctAnswer.length());
//
//        String[] correctLetters = correctAnswer.split("");
//        String[] employeeLetters = employeeAnswers.split("");
//
//        for (int i = 0; i < derailedResults.size(); i++) {
//            derailedResults.add(i, questions.get(i) +
//                    "\nВерный ответ: " + correctLetters[i] +
//                    "\nДан ответ: " + employeeLetters[i]);
//        }
//
//
//        return derailedResults.toString();
//    }
}
