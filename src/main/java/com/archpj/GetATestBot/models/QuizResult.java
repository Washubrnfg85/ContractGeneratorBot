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

    private String spec;

    private String results;

    @Transient
    private String score;

    public QuizResult(long telegramId, String spec, String results, String score) {
        this.telegramId = telegramId;
        this.spec = spec;
        this.results = results;
        this.score = score;
    }

    public QuizResult() {

    }

    public String getSpec() {
        return spec;
    }

}
