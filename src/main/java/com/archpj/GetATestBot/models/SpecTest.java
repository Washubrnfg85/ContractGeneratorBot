package com.archpj.GetATestBot.models;

import com.archpj.GetATestBot.services.SpecTestService;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "spec_test_data")
public class SpecTest {
    @Transient
    private SpecTestService specTestService;

    @Transient
    private List<String> questions;
    @Transient
    private String correctAnswers;
    @Id
    private int questionId;
    private String spec;
    private String question;
    private String correctAnswer;
    @Transient
    private String employeeAnswers;

    public SpecTest(SpecTestService specTestService, String spec) {
        this.specTestService = specTestService;

        questions = specTestService.loadSpecQuestions(spec);
        this.employeeAnswers = "";
    }

    public void recordEmployeeAnswer(String answer) {
        employeeAnswers += answer;
    }

    public List<String> getQuestions() {
        return questions;
    }
}
