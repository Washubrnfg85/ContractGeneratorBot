package com.archpj.GetATestBot.components;

import java.util.ArrayList;
import java.util.List;

public enum MedicalSpecialisations {
    IMPLANT ("Имплантология"),
    SURGERY ("Хирургия"),
    THERAPY ("Терапия"),
    HYGIENIC_CLEANING ("Гигиеническая чистка"),
    ORTHOPEDIC ("Ортопедия"),
    ORTHODONTIC ("Ортодонтия"),
    PRICE ("Прайс-лист")
    ;

    private final String specialisation;

    MedicalSpecialisations(String specialisation) {
        this.specialisation = specialisation;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public List<String> toList() {
        List<String> result = new ArrayList<>(CommonSpecialisations.values().length);
        for (int i = 0; i < CommonSpecialisations.values().length; i++) {
            result.add(CommonSpecialisations.values()[i].getSpecialisation());
        }

        return result;
    }
}
