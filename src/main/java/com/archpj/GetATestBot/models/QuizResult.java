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
    private long employeeId;
    private String employeeName;
    private String topic;
    private String results;
    private Timestamp timestamp;

    public QuizResult() {}

    public QuizResult(long employeeId, String employeeName, String topic, String results, Timestamp timestamp) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.topic = topic;
        this.results = results;
        this.timestamp = timestamp;
    }
}
