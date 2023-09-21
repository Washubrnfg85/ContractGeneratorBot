package com.archpj.GetATestBot.services;

import com.archpj.GetATestBot.models.QuizQuestion;
import com.archpj.GetATestBot.models.QuizResult;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Getter
@Setter
public class SessionServiceImpl implements SessionService {

    private static QuizQuestionService quizQuestionService;
    private static QuizResultsService quizResultsService;

    @Autowired
    public SessionServiceImpl(QuizQuestionService quizQuestionService, QuizResultsService quizResultsService) {
        SessionServiceImpl.quizQuestionService = quizQuestionService;
        SessionServiceImpl.quizResultsService = quizResultsService;
        // говноконструктор - переписать.
    }

    public static List<QuizQuestion> loadQuizQuestions(String topic) {
        return SessionServiceImpl.quizQuestionService.loadQuizQuestions(topic);
    }

    public static void saveQuizResult(QuizResult quizResult) {
        SessionServiceImpl.quizResultsService.saveQuizResult(quizResult);
    }

//    public void addQuestion(QuizQuestion quiz) {
//        quizRepository.save(quiz);
//    }
//
//    public boolean ifPresents(QuizQuestion quiz) {
//        return quizRepository.existsQuizByQuestion(quiz.getQuestion());
//    }
}
