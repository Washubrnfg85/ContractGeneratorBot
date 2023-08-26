package com.archpj.GetATestBot.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "spec_results_data")
public class SpecResult {

    @Id
    @GeneratedValue
    private int id;

    private long telegramId;

    private String spec;

    private String results;

    private String score;

    public SpecResult(long telegramId, String spec, String results, String score) {
        this.telegramId = telegramId;
        this.spec = spec;
        this.results = results;
        this.score = score;
    }

    public SpecResult() {

    }

    public String getSpec() {
        return spec;
    }

}
