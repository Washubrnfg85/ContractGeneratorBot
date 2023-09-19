package com.archpj.GetATestBot.database;

import com.archpj.GetATestBot.models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {

    @Query(value = "select question from quiz_questions where topic = ?1 order by question_id asc", nativeQuery = true)
    List<String> loadQuizQuestions(String topic);

    @Query(value = "select correct_answer from quiz_questions where topic = ?1 order by question_id asc", nativeQuery = true)
    List<String> loadCorrectAnswers(String topic);

    boolean existsQuizByQuestion(String questionBody);
}
