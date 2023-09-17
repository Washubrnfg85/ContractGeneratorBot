package com.archpj.GetATestBot.models;

import com.archpj.GetATestBot.services.QuizService;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "quiz_questions")
public class Quiz {
    @Transient
    private QuizService quizService;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int questionId;
    private String topic;
    private String question;
    private String correctAnswer;


    @Transient
    private List<String> questions;

    @Transient
    private String correctAnswers;

    @Transient
    private String employeeAnswers;

    public Quiz() {}

    public Quiz(QuizService quizService, String topic) { //переписать конструктор
        this.quizService = quizService;
        this.topic = topic;

        questions = quizService.loadQuizQuestions(topic);   //так лучше не делать
        correctAnswer = quizService.loadCorrectAnswers(topic);
        this.employeeAnswers = "";
    }

    public List<String> getQuestions() {
        return questions;
    }

    public void setEmployeeAnswers(String employeeAnswers) {
        this.employeeAnswers = employeeAnswers;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "topic='" + topic + '\'' +
                ", question='" + question + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                '}';
    }
}
