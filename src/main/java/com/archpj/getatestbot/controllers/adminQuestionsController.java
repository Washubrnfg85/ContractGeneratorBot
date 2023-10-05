package com.archpj.getatestbot.controllers;

import com.archpj.getatestbot.components.CommonTopics;
import com.archpj.getatestbot.database.QuizQuestionRepository;
import com.archpj.getatestbot.models.QuizQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class adminQuestionsController {

    private final QuizQuestionRepository quizQuestionRepository;

    @Autowired
    public adminQuestionsController(QuizQuestionRepository quizQuestionRepository) {
        this.quizQuestionRepository = quizQuestionRepository;
    }

    @GetMapping("admin/questions/show_questions")
    public String showAllQuizQuestions(Model model) {
        QuizQuestion quizQuestion = new QuizQuestion();
        model.addAttribute("quizQuestion", quizQuestion);

        List<QuizQuestion> quizQuestions = quizQuestionRepository.findAll();
        model.addAttribute("quizQuestions", quizQuestions);

        List<String> topicVariants = CommonTopics.toList();
        model.addAttribute("topicVariants", topicVariants);

        return "admin/questions/show_questions";
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
    public void submitForm(@ModelAttribute("quizQuestion") QuizQuestion quizQuestion, Model model) {
        quizQuestionRepository.save(quizQuestion);
//        String topic = quizQuestion.getTopic();
//        sortQuestionsByTopic(topic, model);

    }

    @PostMapping("admin/questions/sort_questions")
    public void getTopicToSortQuestions(@ModelAttribute("quizQuestion") QuizQuestion quizQuestion, Model model) {
        QuizQuestion anotherQuizQuestion = new QuizQuestion();
        model.addAttribute("anotherQuizQuestion", anotherQuizQuestion);

        List<String> topicVariants = CommonTopics.toList();
        model.addAttribute("topicVariants", topicVariants);

        String topic = quizQuestion.getTopic();
        sortQuestionsByTopic(topic, model);
    }

    public String sortQuestionsByTopic(String topic, Model model) {
        List<QuizQuestion> quizQuestions = quizQuestionRepository.loadQuizQuestions(topic);
        model.addAttribute("quizQuestions", quizQuestions);

        return "admin/questions/sort_questions";
    }

    @GetMapping("admin/show_data")
    public String getData() {
        return "admin/show_data";
    }
}
