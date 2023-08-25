package com.archpj.GetATestBot.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "spec_results_data")
public class SpecResult {

    @Id
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

    public long getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(long telegramId) {
        this.telegramId = telegramId;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
