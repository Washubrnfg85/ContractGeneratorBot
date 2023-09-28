package com.archpj.getatestbot.models;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "quiz_questions")
public class QuizQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int questionId;
    private String topic;
    private String question;
    private String correctAnswer;

}
