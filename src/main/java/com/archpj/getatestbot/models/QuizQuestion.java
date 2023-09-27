package com.archpj.getatestbot.models;

import com.archpj.getatestbot.services.QuizQuestionService;
import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "quiz_questions")
public class QuizQuestion {

    @Transient
    private QuizQuestionService quizQuestionService;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int questionId;
    private String topic;
    private String question;
    private String correctAnswer;

}
