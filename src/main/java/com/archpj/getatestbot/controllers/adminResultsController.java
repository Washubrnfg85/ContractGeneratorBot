package com.archpj.getatestbot.controllers;

import com.archpj.getatestbot.database.QuizQuestionRepository;
import com.archpj.getatestbot.database.QuizResultsRepository;
import com.archpj.getatestbot.models.QuizQuestion;
import com.archpj.getatestbot.models.QuizResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class adminResultsController {

    private QuizResultsRepository quizResultsRepository;
    private QuizQuestionRepository quizQuestionRepository;

    @Autowired
    public adminResultsController(QuizResultsRepository quizResultsRepository) {
        this.quizResultsRepository = quizResultsRepository;
    }

    @GetMapping("/")
    public String showAllQuizResults(Model model) {
        List<QuizResult> QuizResults = quizResultsRepository.findAll();
        model.addAttribute("QuizResults", QuizResults);

        return "admin/results/show_results";
    }

    @GetMapping("/{id}")
    public String showDetailedResult(@PathVariable("id") int id, Model model) {
        QuizResult quizResult = quizResultsRepository.getReferenceById(id);

        if (quizResult.getQuestionIds() != null) {
            List<Optional<QuizQuestion>> questions = new ArrayList<>();
            for (Integer questionId : quizResult.getQuestionIds()) {
                questions.add(quizQuestionRepository.findById(questionId));
            }
            model.addAttribute("questions", questions);
        }

        model.addAttribute("quizResult", quizResult);

        return "admin/results/show_detailed_result";
    }

}
