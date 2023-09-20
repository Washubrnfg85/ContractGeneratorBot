package com.archpj.GetATestBot.database;

import com.archpj.GetATestBot.models.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface QuizResultsRepository extends JpaRepository<QuizResult, Long> {

}
