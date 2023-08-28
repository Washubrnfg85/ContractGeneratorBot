package com.archpj.GetATestBot.services;

import com.archpj.GetATestBot.database.SpecResultsRepository;
import com.archpj.GetATestBot.models.SpecResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class SpecResultsService {

    private SpecResultsRepository specResultsRepository;


    @Autowired
    public SpecResultsService(SpecResultsRepository specResultsRepository) {
        this.specResultsRepository = specResultsRepository;
    }

    public List<SpecResult> findAllById(long telegramId) {
        return specResultsRepository.findAllById(Collections.singleton(telegramId));
    }

    public SpecResult findEmployeeSpecResult(long telegramId, String spec) {
        SpecResult result = null;
        for(SpecResult specResult : findAllById(telegramId)) {
            if(specResult.getSpec().equals(spec)) {
                result = specResult;
            }
        }
        return result;
    }

    public void saveSpecResult(SpecResult specResult) {
        specResultsRepository.save(specResult);
    }

    public void updateSpecResult(SpecResult specResult) {
        Long telegramId = specResult.getTelegramId();
        String results = specResult.getResults();
        String score = specResult.getScore();
        String spec = specResult.getSpec();

        specResultsRepository.updateSpecResult(telegramId, results, score, spec);
    }

    public boolean checkIfPresents(SpecResult specResult) {
        Long telegramId = specResult.getTelegramId();
        String spec = specResult.getSpec();

        return specResultsRepository.checkIfPresents(telegramId, spec);
    }
}
