package com.archpj.GetATestBot.components;

public enum Specialisations {
    IMPLANT ("Имплантология"),
    SURGERY ("Хирургия"),
    THERAPY ("Терапия"),
    HYGIENIC_CLEANING ("Гигиеническая чистка"),
    ORTHOPEDIC ("Ортопедия"),
    ORTHODONTIC ("Ортодонтия");

    private String specialisation;

    Specialisations(String specialisation) {
        this.specialisation = specialisation;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public static String[] toArray() {
        String[] result = new String[Specialisations.values().length];
        for (int i = 0; i < result.length; i++) {
            result[i] = Specialisations.values()[i].getSpecialisation();
        }

        return result;
    }
}
