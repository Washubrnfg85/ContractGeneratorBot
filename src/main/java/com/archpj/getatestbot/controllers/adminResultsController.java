package com.archpj.getatestbot.controllers;

import com.archpj.getatestbot.database.QuizResultsRepository;
import com.archpj.getatestbot.models.QuizResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class adminResultsController {

    private QuizResultsRepository quizResultsRepository;

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

}
