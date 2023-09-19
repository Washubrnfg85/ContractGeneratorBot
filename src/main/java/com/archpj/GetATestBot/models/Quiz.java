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
}
