package com.archpj.getatestbot.services;

import com.archpj.getatestbot.models.QuizQuestion;
import com.archpj.getatestbot.models.QuizResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SessionServiceImpl implements SessionService {

    private QuizQuestionService quizQuestionService;
    private QuizResultsService quizResultsService;

    @Autowired
    public SessionServiceImpl(QuizQuestionService quizQuestionService, QuizResultsService quizResultsService) {
        this.quizQuestionService = quizQuestionService;
        this.quizResultsService = quizResultsService;
    }

    public List<QuizQuestion> loadQuizQuestions(String topic) {
        return quizQuestionService.loadQuizQuestions(topic);
    }

    public void saveQuizResult(QuizResult quizResult) {
        quizResultsService.saveQuizResult(quizResult);
    }

    public void addQuestion(QuizQuestion quizQuestion) {
        quizQuestionService.addQuestion(quizQuestion);
    }
}
