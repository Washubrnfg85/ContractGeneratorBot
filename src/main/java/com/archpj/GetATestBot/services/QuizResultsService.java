package com.archpj.GetATestBot.services;

import com.archpj.GetATestBot.database.QuizResultsRepository;
import com.archpj.GetATestBot.models.QuizResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class QuizResultsService {

    private static QuizResultsRepository quizResultsRepository;

    public QuizResultsService(QuizResultsRepository quizResultsRepository) {
        QuizResultsService.quizResultsRepository = quizResultsRepository;
    }

    public static void saveQuizResult(QuizResult quizResult) {
        quizResultsRepository.save(quizResult);
    }

}
