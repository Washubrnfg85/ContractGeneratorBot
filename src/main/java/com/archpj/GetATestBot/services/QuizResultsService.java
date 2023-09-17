package com.archpj.GetATestBot.services;

import com.archpj.GetATestBot.database.QuizResultsRepository;
import com.archpj.GetATestBot.models.QuizResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class QuizResultsService {

    private QuizResultsRepository quizResultsRepository;


    @Autowired
    public QuizResultsService(QuizResultsRepository quizResultsRepository) {
        this.quizResultsRepository = quizResultsRepository;
    }

    public void saveQuizResult(QuizResult quizResult) {
        quizResultsRepository.save(quizResult);
    }

    public void updateQuizResult(QuizResult quizResult) {
        Long telegramId = quizResult.getTelegramId();
        String employeeName = quizResult.getEmployeeName();
        String results = quizResult.getResults();
        String topic = quizResult.gettopic();
        Timestamp timestamp = quizResult.getTimestamp();

        quizResultsRepository.updateSpecResult(telegramId, employeeName, results, topic, timestamp);
    }

    public boolean checkIfPresents(QuizResult quizResult) {
        Long telegramId = quizResult.getTelegramId();
        String topic = quizResult.gettopic();

        return quizResultsRepository.checkIfPresents(telegramId, topic);
    }

//    Method hasn't been tested yet.
//
//    public String getDetailedResult(Quiz quiz, QuizResult quizResult) {
//        List<String> questions = quiz.getQuestions();
//        String results = quizResult.getResults();
//
//        String[] correctAndEmployeeResults = results.split("\n");
//        String correctAnswer = correctAndEmployeeResults[0];
//        String employeeAnswers = correctAndEmployeeResults[1];
//
//        List<String> derailedResults = new ArrayList<>(correctAnswer.length());
//
//        String[] correctLetters = correctAnswer.split("");
//        String[] employeeLetters = employeeAnswers.split("");
//
//        for (int i = 0; i < derailedResults.size(); i++) {
//            derailedResults.add(i, questions.get(i) +
//                    "\nВерный ответ: " + correctLetters[i] +
//                    "\nДан ответ: " + employeeLetters[i]);
//        }
//
//
//        return derailedResults.toString();
//    }

}
