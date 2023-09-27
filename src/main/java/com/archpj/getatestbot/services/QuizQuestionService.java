package com.archpj.getatestbot.services;

import com.archpj.getatestbot.database.QuizQuestionRepository;
import com.archpj.getatestbot.models.QuizQuestion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class QuizQuestionService {

    private static QuizQuestionRepository quizQuestionRepository;

    public QuizQuestionService(QuizQuestionRepository quizQuestionRepository) {
        QuizQuestionService.quizQuestionRepository = quizQuestionRepository;
    }

    public List<QuizQuestion> loadAllQuestions() {
        return quizQuestionRepository.findAll();
    }

    public List<QuizQuestion> loadQuizQuestions(String topic) {
        return QuizQuestionService.quizQuestionRepository.loadQuizQuestions(topic);
    }

    public void addQuestion(QuizQuestion quizQuestion) {
        quizQuestionRepository.save(quizQuestion);
    }
}