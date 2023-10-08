package com.archpj.getatestbot.services;

import com.archpj.getatestbot.models.QuizQuestion;
import com.archpj.getatestbot.models.QuizResult;

import java.util.List;

public interface DataService {

    List<QuizQuestion> loadQuizQuestions(String topic);

    void saveQuizResult(QuizResult quizResult);

    void addQuestion(QuizQuestion quizQuestion);
}
