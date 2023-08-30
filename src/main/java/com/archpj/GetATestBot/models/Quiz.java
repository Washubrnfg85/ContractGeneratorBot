package com.archpj.GetATestBot.models;

import com.archpj.GetATestBot.services.QuizService;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "quiz_questions")
public class Quiz {
    @Id
    private int questionId;
    private String specialisation;
    private String question;
    private String correctAnswer;


    @Transient
    private QuizService quizService;

    @Transient
    private List<String> questions;

    @Transient
    private String correctAnswers;

    @Transient
    private String employeeAnswers;


    public Quiz(QuizService quizService, String specialisation) {
        this.quizService = quizService;
        this.specialisation = specialisation;

        questions = quizService.loadQuizQuestions(specialisation);
        correctAnswer = quizService.loadCorrectAnswers(specialisation);
        this.employeeAnswers = "";
    }

    public List<String> getQuestions() {
        return questions;
    }

    public void setEmployeeAnswers(String employeeAnswers) {
        this.employeeAnswers = employeeAnswers;
    }


//    Method hasn't been tested yet.
//
//    public String getDetailedResult() {
//        List<String> derailedResults = new ArrayList<>(questions.size());
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
