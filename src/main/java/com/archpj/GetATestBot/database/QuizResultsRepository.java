package com.archpj.GetATestBot.database;

import com.archpj.GetATestBot.models.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface QuizResultsRepository extends JpaRepository<QuizResult, Long> {

    @Query(value = "SELECT EXISTS (SELECT * FROM quiz_results WHERE telegram_id = ?1 and topic = ?2);", nativeQuery = true)
    boolean checkIfPresents(Long telegramId, String topic);

//    List<QuizResult> get
}
