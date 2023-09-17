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

    @Modifying
    @Query(value = "update quiz_results set telegram_id = ?1, employee_name = ?2, results = ?3, topic = ?4, timestamp = ?5 where telegram_id = ?1 and topic = ?4", nativeQuery = true)
    void updateSpecResult(Long telegramId, String employeeName, String results, String topic, Timestamp timestamp);

    @Query(value = "SELECT EXISTS (SELECT * FROM quiz_results WHERE telegram_id = ?1 and topic = ?2);", nativeQuery = true)
    boolean checkIfPresents(Long telegramId, String topic);

//    List<QuizResult> get
}
