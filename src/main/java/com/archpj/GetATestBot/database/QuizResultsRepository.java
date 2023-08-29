package com.archpj.GetATestBot.database;

import com.archpj.GetATestBot.models.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizResultsRepository extends JpaRepository<QuizResult, Long> {

    @Modifying
    @Query(value = "update quiz_results set telegram_id = ?1, results = ?2, score = ?3, specialisation = ?4 where telegram_id = ?1 and specialisation = ?4", nativeQuery = true)
    void updateSpecResult(Long telegramId, String results, String score, String specialisation);

    @Query(value = "SELECT EXISTS (SELECT * FROM quiz_results WHERE telegram_id = ?1 and specialisation = ?2);", nativeQuery = true)
    boolean checkIfPresents(Long telegramId, String specialisation);
}
