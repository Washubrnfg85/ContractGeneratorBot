package com.archpj.GetATestBot.services;

import com.archpj.GetATestBot.models.QuizQuestion;
import com.archpj.GetATestBot.models.QuizResult;
import lombok.Getter;
import lombok.Setter;
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
        // говноконструктор - переписать.
    }

    public List<QuizQuestion> loadQuizQuestions(String topic) {
        return quizQuestionService.loadQuizQuestions(topic);
    }

    public void saveQuizResult(QuizResult quizResult) {
        quizResultsService.saveQuizResult(quizResult);
    }

//    public void addQuestion(QuizQuestion quiz) {
//        quizRepository.save(quiz);
//    }
//
//    public boolean ifPresents(QuizQuestion quiz) {
//        return quizRepository.existsQuizByQuestion(quiz.getQuestion());
//    }
}
