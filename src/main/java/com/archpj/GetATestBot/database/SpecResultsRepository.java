package com.archpj.GetATestBot.database;

import com.archpj.GetATestBot.models.SpecResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecResultsRepository extends JpaRepository<SpecResult, Long> {

    @Modifying
    @Query(value = "update spec_results set telegram_id = ?1, results = ?2, score = ?3, spec = ?4 where telegram_id = ?1 and spec = ?4", nativeQuery = true)
    void updateSpecResult(Long telegramId, String results, String score, String spec);

    @Query(value = "SELECT EXISTS (SELECT * FROM spec_results WHERE telegram_id = ?1 and spec = ?2);", nativeQuery = true)
    boolean checkIfPresents(Long telegramId, String spec);
}
