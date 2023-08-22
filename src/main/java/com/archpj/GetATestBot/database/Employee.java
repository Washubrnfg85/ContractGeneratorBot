package com.archpj.GetATestBot.database;

import jakarta.persistence.Entity;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Entity(name = "employees")
public class Employee {

    @Id
    private long id;
    private String name;

}
