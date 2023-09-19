package com.archpj.GetATestBot.services;

import com.archpj.GetATestBot.database.QuizRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
@Setter
public class SessionService {

    private static QuizRepository quizRepository;

//    @Autowired
//    public SessionService(QuizRepository quizRepository) {
//        this.quizRepository = quizRepository;
//    }

    public static List<String> loadQuizQuestions(String topic) {
        return quizRepository.loadQuizQuestions(topic);
    }

    public static String loadCorrectAnswers(String topic) {
        List<String> correctAnswers = quizRepository.loadCorrectAnswers(topic);
        String result = "";

        for (int i = 0; i < correctAnswers.size(); i++) {
            result += correctAnswers.get(i);
        }

        return result;
    }

//    public void addQuestion(Quiz quiz) {
//        quizRepository.save(quiz);
//    }
//
//    public boolean ifPresents(Quiz quiz) {
//        return quizRepository.existsQuizByQuestion(quiz.getQuestion());
//    }

}
