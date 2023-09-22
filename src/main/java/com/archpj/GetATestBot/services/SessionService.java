package com.archpj.GetATestBot.services;

import com.archpj.GetATestBot.models.QuizQuestion;
import com.archpj.GetATestBot.models.QuizResult;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SessionService {

    List<QuizQuestion> loadQuizQuestions(String topic);
    void saveQuizResult(QuizResult quizResult);
}
