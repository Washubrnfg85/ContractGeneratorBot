package com.archpj.getatestbot.controllers;

import com.archpj.getatestbot.components.CommonTopics;
import com.archpj.getatestbot.database.QuizQuestionRepository;
import com.archpj.getatestbot.database.QuizResultsRepository;
import com.archpj.getatestbot.models.QuizQuestion;
import com.archpj.getatestbot.models.QuizResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class adminController {

    @Autowired
    private QuizQuestionRepository quizQuestionRepository;

    @Autowired
    private QuizResultsRepository quizResultsRepository;


    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/admin/admin")
    public String admin() {
        return "admin/admin";
    }

    @GetMapping("admin/questions/add_question")
    public String addQuestion(Model model) {
        QuizQuestion quizQuestion = new QuizQuestion();
        model.addAttribute("quizQuestion", quizQuestion);

        List<String> topicVariants = CommonTopics.toList();
        model.addAttribute("topicVariants", topicVariants);

        List<String> answerVariants = Arrays.asList("A", "B", "C", "D");
        model.addAttribute("answerVariants", answerVariants);

        return "admin/questions/add_question";
    }

    @PostMapping("admin/questions/add_question")
    public String submitForm(@ModelAttribute("quizQuestion") QuizQuestion quizQuestion) {
        quizQuestionRepository.save(quizQuestion);

        return "admin/questions/add_question_success";
    }

    @GetMapping("admin/show_data")
    public String getData() {
        return "admin/show_data";
    }

    @GetMapping("admin/results/show_results")
    public String showAllQuizResults(Model model) {
        List<QuizResult> QuizResults = quizResultsRepository.findAll();
        model.addAttribute("QuizResults", QuizResults);

        return "admin/results/show_results";
    }

    @GetMapping("admin/questions/show_questions")
    public String showAllQuizQuestions(Model model) {
        List<QuizQuestion> QuizQuestions = quizQuestionRepository.findAll();
        model.addAttribute("QuizQuestions", QuizQuestions);

        return "admin/questions/show_questions";
    }
}
