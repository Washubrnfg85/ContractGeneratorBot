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

    private final QuizRepository quizRepository;
    private String topic;

    @Autowired
    public SessionService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public List<String> loadQuizQuestions() {
        return quizRepository.loadQuizQuestions(topic);
    }

    public String loadCorrectAnswers() {
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
