package com.archpj.GetATestBot.services;

import com.archpj.GetATestBot.database.QuizQuestionRepository;
import com.archpj.GetATestBot.models.QuizQuestion;
import com.archpj.GetATestBot.models.QuizResult;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
@Setter
public class SessionService {

    private static QuizQuestionService quizQuestionService;
    private static QuizResultsService quizResultsService;

    @Autowired
    public SessionService(QuizQuestionService quizQuestionService, QuizResultsService quizResultsService) {
        SessionService.quizQuestionService = quizQuestionService;
        SessionService.quizResultsService = quizResultsService;
    }

    public static List<QuizQuestion> loadQuizQuestions(String topic) {
        return SessionService.quizQuestionService.loadQuizQuestions(topic);
    }

    public static void saveQuizResult(QuizResult quizResult) {
        SessionService.saveQuizResult(quizResult);
    }

//    public void addQuestion(QuizQuestion quiz) {
//        quizRepository.save(quiz);
//    }
//
//    public boolean ifPresents(QuizQuestion quiz) {
//        return quizRepository.existsQuizByQuestion(quiz.getQuestion());
//    }

}
