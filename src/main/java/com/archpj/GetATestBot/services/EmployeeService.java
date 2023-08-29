package com.archpj.GetATestBot.services;

import com.archpj.GetATestBot.models.Employee;
import com.archpj.GetATestBot.database.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    public boolean hasEmployee(long telegramId) {
        return employeeRepository.existsById(telegramId);
    }
}
