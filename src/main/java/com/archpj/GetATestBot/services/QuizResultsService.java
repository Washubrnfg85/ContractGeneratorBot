package com.archpj.GetATestBot.services;

import com.archpj.GetATestBot.database.QuizResultsRepository;
import com.archpj.GetATestBot.models.QuizResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
@Transactional
public class QuizResultsService {

    private QuizResultsRepository quizResultsRepository;


    @Autowired
    public QuizResultsService(QuizResultsRepository quizResultsRepository) {
        this.quizResultsRepository = quizResultsRepository;
    }

    public void saveQuizResult(QuizResult quizResult) {
        quizResultsRepository.save(quizResult);
    }

    public void updateQuizResult(QuizResult quizResult) {
        Long telegramId = quizResult.getTelegramId();
        String employeeName = quizResult.getEmployeeName();
        String results = quizResult.getResults();
        String specialisation = quizResult.getSpecialisation();
        Timestamp timestamp = quizResult.getTimestamp();

        quizResultsRepository.updateSpecResult(telegramId, employeeName, results, specialisation, timestamp);
    }

    public boolean checkIfPresents(QuizResult quizResult) {
        Long telegramId = quizResult.getTelegramId();
        String specialisation = quizResult.getSpecialisation();

        return quizResultsRepository.checkIfPresents(telegramId, specialisation);
    }


}
