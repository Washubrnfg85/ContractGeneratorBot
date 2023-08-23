package com.archpj.GetATestBot.services;

import com.archpj.GetATestBot.database.SpecResultsRepository;
import com.archpj.GetATestBot.models.SpecResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class SpecResultService {

    private SpecResultsRepository specResultsRepository;

    @Autowired
    public SpecResultService(SpecResultsRepository specResultsRepository) {
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
}
