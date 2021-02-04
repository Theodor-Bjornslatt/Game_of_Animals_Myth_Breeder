package com.company.Enums;

public enum Gender {
    MALE("Male"),
    FEMALE("Female");

    public String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public String string() {
        return gender;
    }
}
