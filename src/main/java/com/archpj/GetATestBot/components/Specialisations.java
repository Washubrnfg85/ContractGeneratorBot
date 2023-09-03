package com.archpj.GetATestBot.components;

import java.util.ArrayList;
import java.util.List;

public enum Specialisations {
    IMPLANT ("География"),
    SURGERY ("Физика"),
    THERAPY ("Астрономия"),
    HYGIENIC_CLEANING ("Анатомия"),
    ORTHOPEDIC ("История"),
//    ORTHODONTIC ("Жизнь в Алкоцентавре")
    ;

    private String specialisation;

    Specialisations(String specialisation) {
        this.specialisation = specialisation;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public static List<String> toList() {
        List<String> result = new ArrayList<>(Specialisations.values().length);
        for (int i = 0; i < Specialisations.values().length; i++) {
            result.add(Specialisations.values()[i].getSpecialisation());
        }

        return result;
    }
}
