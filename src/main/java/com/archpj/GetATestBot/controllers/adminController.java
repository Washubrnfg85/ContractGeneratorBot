package com.archpj.GetATestBot.controllers;

import com.archpj.GetATestBot.components.CommonTopics;
import com.archpj.GetATestBot.models.Quiz;
import com.archpj.GetATestBot.services.QuizService;
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
    private final QuizService quizService;

    public adminController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("admin/add_question")
    public String addQuestion(Model model) {
        Quiz quiz = new Quiz();
        model.addAttribute("quiz", quiz);

        List<String> topicVariants = CommonTopics.toList();
        model.addAttribute("topicVariants", topicVariants);

        List<String> answerVariants = Arrays.asList("A", "B", "C", "D");
        model.addAttribute("answerVariants", answerVariants);

        return "admin/add_question";
    }

    @PostMapping("admin/add_question")
    public String submitForm(@ModelAttribute("quiz") Quiz quiz) {
        quizService.addQuestion(quiz);

        return "admin/add_question_success";
    }

    @GetMapping("admin/get_data")
    public String getData() {
        return "admin/get_data";
    }
}
