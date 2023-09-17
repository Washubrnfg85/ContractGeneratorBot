package com.archpj.GetATestBot.components;

import java.util.ArrayList;
import java.util.List;

public enum MedicalTopics {
    IMPLANT ("Имплантология"),
    SURGERY ("Хирургия"),
    THERAPY ("Терапия"),
    HYGIENIC_CLEANING ("Гигиеническая чистка"),
    ORTHOPEDIC ("Ортопедия"),
    ORTHODONTIC ("Ортодонтия"),
    PRICE ("Прайс-лист")
    ;

    private final String topic;

    MedicalTopics(String topic) {
        this.topic = topic;
    }

    public String gettopic() {
        return topic;
    }

    public List<String> toList() {
        List<String> result = new ArrayList<>(CommonTopics.values().length);
        for (int i = 0; i < CommonTopics.values().length; i++) {
            result.add(CommonTopics.values()[i].gettopic());
        }

        return result;
    }
}
