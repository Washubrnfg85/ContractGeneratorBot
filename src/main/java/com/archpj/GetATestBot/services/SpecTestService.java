package com.archpj.GetATestBot.services;

import com.archpj.GetATestBot.database.SpecTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SpecTestService {

    private final SpecTestRepository specTestRepository;

    @Autowired
    public SpecTestService(SpecTestRepository specTestRepository) {
        this.specTestRepository = specTestRepository;
    }

    public List<String> loadSpecQuestions(String spec) {
        return specTestRepository.loadSpecQuestions(spec);
    }

    public String loadCorrectAnswers(String spec) {
        List<String> correctAnswers = specTestRepository.loadCorrectAnswers(spec);
        String result = "";

        for (int i = 0; i < correctAnswers.size(); i++) {
            result += correctAnswers.get(i);
        }

        return result;
    }
}
