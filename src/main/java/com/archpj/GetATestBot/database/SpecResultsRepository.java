package com.archpj.GetATestBot.database;

import com.archpj.GetATestBot.models.SpecResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecResultsRepository extends JpaRepository<SpecResult, Long> {
}
