package com.archpj.getatestbot.services;

import com.archpj.getatestbot.database.QuizQuestionRepository;
import com.archpj.getatestbot.database.QuizResultsRepository;
import com.archpj.getatestbot.models.QuizQuestion;
import com.archpj.getatestbot.models.QuizResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RepoDataService implements DataService {

    private final QuizQuestionRepository quizQuestionRepository;
    private final QuizResultsRepository quizResultsRepository;


    @Autowired
    public RepoDataService(QuizQuestionRepository quizQuestionRepository, QuizResultsRepository quizResultsRepository) {
        this.quizQuestionRepository = quizQuestionRepository;
        this.quizResultsRepository = quizResultsRepository;
    }

    public List<QuizQuestion> loadQuizQuestions(String topic) {
        return quizQuestionRepository.loadQuizQuestions(topic);
    }

    public void saveQuizResult(QuizResult quizResult) {
        quizResultsRepository.save(quizResult);
    }

    public void addQuestion(QuizQuestion quizQuestion) {
        quizQuestionRepository.save(quizQuestion);
    }
}
