package com.archpj.GetATestBot.database;

import com.archpj.GetATestBot.models.SpecTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecTestRepository extends JpaRepository<SpecTest, Integer> {

    @Query(value = "select question from spec_test_data where spec = ?1", nativeQuery = true)
    List<String> loadSpecQuestions(String spec);

    @Query(value = "select correct_answer from spec_test_data where spec = ?1", nativeQuery = true)
    List<String> loadCorrectAnswers(String spec);
}
