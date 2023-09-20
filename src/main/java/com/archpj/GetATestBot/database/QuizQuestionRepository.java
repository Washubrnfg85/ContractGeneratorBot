package com.archpj.GetATestBot.database;

import com.archpj.GetATestBot.models.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Integer> {

    @Query(value = "SELECT * FROM quiz_questions WHERE topic = ?1 ORDER BY random() ASC", nativeQuery = true)
    List<QuizQuestion> loadQuizQuestions(String topic);
}