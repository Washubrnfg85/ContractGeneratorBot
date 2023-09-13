package com.archpj.GetATestBot.components;

import java.util.ArrayList;
import java.util.List;

public enum CommonSpecialisations {
    GEOGRAPHY ("География"),
    PHYSICS ("Физика"),
    ASTRONOMY ("Астрономия"),
    ANATOMY ("Анатомия"),
    HISTORY ("История")
    ;

    private final String specialisation;

    CommonSpecialisations(String specialisation) {
        this.specialisation = specialisation;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public static List<String> toList() {
        List<String> result = new ArrayList<>(CommonSpecialisations.values().length);
        for (int i = 0; i < CommonSpecialisations.values().length; i++) {
            result.add(CommonSpecialisations.values()[i].getSpecialisation());
        }

        return result;
    }
}
