package com.archpj.GetATestBot.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "quiz_results")
public class QuizResult {

    @Id
    @GeneratedValue
    private int id;

    private long telegramId;

    private String specialisation;

    private String results;

    @Transient
    private String score;

    public QuizResult(long telegramId, String specialisation, String results, String score) {
        this.telegramId = telegramId;
        this.specialisation = specialisation;
        this.results = results;
        this.score = score;
    }

    public QuizResult() {

    }

    public String getSpecialisation() {
        return specialisation;
    }

}
