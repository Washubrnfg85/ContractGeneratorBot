package com.archpj.GetATestBot.components;

public enum Specs {
    IMPLANT ("Имплантология"),
    SURGERY ("Хирургия"),
    THERAPY ("Терапия"),
    HYGIENIC_CLEANING ("Гигиеническая чистка"),
    ORTHOPEDIC ("Ортопедия"),
    ORTHODONTIC ("Ортодонтия");

    private String spec;

    Specs(String spec) {
        this.spec = spec;
    }

    public String getSpec() {
        return spec;
    }
}
