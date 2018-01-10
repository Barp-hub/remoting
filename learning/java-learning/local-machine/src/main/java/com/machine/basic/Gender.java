package com.machine.basic;

public enum Gender {
    M("m", "男"), F("f", "女");

    private Gender(String gender, String description) {
        this.gender = gender;
        this.description = description;
    }

    private String gender;

    private String description;

    public String getGender() {
        return gender;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Gender{" +
                "gender='" + gender + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
