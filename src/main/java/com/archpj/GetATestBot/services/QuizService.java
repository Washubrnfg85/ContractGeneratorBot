package com.archpj.GetATestBot.services;

import com.archpj.GetATestBot.database.QuizRepository;
import com.archpj.GetATestBot.models.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class QuizService {

    private final QuizRepository quizRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public List<String> loadQuizQuestions(String specialisation) {
        return quizRepository.loadQuizQuestions(specialisation);
    }

    public String loadCorrectAnswers(String specialisation) {
        List<String> correctAnswers = quizRepository.loadCorrectAnswers(specialisation);
        String result = "";

        for (int i = 0; i < correctAnswers.size(); i++) {
            result += correctAnswers.get(i);
        }

        return result;
    }

    public void addQuestion(Quiz quiz) {
        quizRepository.save(quiz);
    }

    public boolean ifPresents(Quiz quiz) {
        return quizRepository.existsQuizByQuestion(quiz.getQuestion());
    }
}
