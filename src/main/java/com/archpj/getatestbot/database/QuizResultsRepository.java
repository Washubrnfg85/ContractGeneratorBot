package com.archpj.getatestbot.database;

import com.archpj.getatestbot.models.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface QuizResultsRepository extends JpaRepository<QuizResult, Integer> {

}
