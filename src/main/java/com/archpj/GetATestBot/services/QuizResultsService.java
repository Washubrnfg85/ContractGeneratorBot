package com.archpj.GetATestBot.services;

import com.archpj.GetATestBot.database.QuizResultsRepository;
import com.archpj.GetATestBot.models.QuizResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class QuizResultsService {

    private QuizResultsRepository quizResultsRepository;


    @Autowired
    public QuizResultsService(QuizResultsRepository quizResultsRepository) {
        this.quizResultsRepository = quizResultsRepository;
    }

    public List<QuizResult> findAllById(long telegramId) {
        return quizResultsRepository.findAllById(Collections.singleton(telegramId));
    }

    public QuizResult findEmployeeSpecResult(long telegramId, String spec) {
        QuizResult result = null;
        for(QuizResult quizResult : findAllById(telegramId)) {
            if(quizResult.getSpec().equals(spec)) {
                result = quizResult;
            }
        }
        return result;
    }

    public void saveSpecResult(QuizResult quizResult) {
        quizResultsRepository.save(quizResult);
    }

    public void updateSpecResult(QuizResult quizResult) {
        Long telegramId = quizResult.getTelegramId();
        String results = quizResult.getResults();
        String score = quizResult.getScore();
        String spec = quizResult.getSpec();

        quizResultsRepository.updateSpecResult(telegramId, results, score, spec);
    }

    public boolean checkIfPresents(QuizResult quizResult) {
        Long telegramId = quizResult.getTelegramId();
        String spec = quizResult.getSpec();

        return quizResultsRepository.checkIfPresents(telegramId, spec);
    }
}
