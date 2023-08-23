package com.archpj.GetATestBot.database;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "employees_data")
public class Employee {

    @Id
    private long telegramId;
    private String name;
    private Timestamp registeredAt;

    public long getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(long telegramId) {
        this.telegramId = telegramId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(Timestamp registeredAt) {
        this.registeredAt = registeredAt;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "telegramId=" + telegramId +
                ", name='" + name + '\'' +
                ", registeredAt=" + registeredAt +
                '}';
    }
}
