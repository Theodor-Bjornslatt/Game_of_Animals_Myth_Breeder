package com.company.Enums;

public enum Gender {
    MALE("Male", "His"),
    FEMALE("Female", "Her");

    public String gender;

    public String pronoun;

    Gender(String gender, String pronoun) {
        this.gender = gender;
        this.pronoun = pronoun;
    }
}
