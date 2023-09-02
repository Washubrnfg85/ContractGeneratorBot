package com.archpj.GetATestBot.controllers;

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

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("first/add_question")
    public String addQuestion(Model model) {
        Quiz quiz = new Quiz();
        model.addAttribute("quiz", quiz);

        List<String> answerVariants = Arrays.asList("A", "B", "C", "D");
        model.addAttribute("answerVariants", answerVariants);

        return "first/add_question";
    }

    @PostMapping("first/add_question")
    public String submitForm(@ModelAttribute("quiz") Quiz quiz) {
        System.out.println(quiz);
        quizService.addQuestion(quiz);

        return "first/add_question_success";
    }

    @GetMapping("first/get_data")
    public String getData() {
        return "first/get_data";
    }
}
