//package com.archpj.GetATestBot.models;
//
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "quiz_questions")
//public class Question {
//
//    @Id
//    @GeneratedValue
//    @Column(name = "question_id")
//    private int id;
//
//    @Column(name = "specialisation")
//    private String specialisation;
//
//    @Column(name = "question")
//    private String questionBody;
//
//    @Column(name = "correct_answer")
//    private String correctAnswer;
//
//    public Question() {}
//
//    public Question(String specialisation, String correctAnswer) {
//        this.specialisation = specialisation;
//        this.correctAnswer = correctAnswer;
//    }
//
//    public String getSpecialisation() {
//        return specialisation;
//    }
//
//    public void setSpecialisation(String specialisation) {
//        this.specialisation = specialisation;
//    }
//
//    public String getQuestionBody() {
//        return questionBody;
//    }
//
//    public void setQuestionBody(String questionBody) {
//        this.questionBody = questionBody;
//    }
//
//    public String getCorrectAnswer() {
//        return correctAnswer;
//    }
//
//    public void setCorrectAnswer(String correctAnswer) {
//        this.correctAnswer = correctAnswer;
//    }
//}
