package com.example.vacations.dimain.models;

import java.time.LocalDate;

public class UserAlIInformationServiceModel {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate startVacation;
    private LocalDate endVacation;
    private Boolean isVacation;

    public UserAlIInformationServiceModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getStartVacation() {
        return startVacation;
    }

    public void setStartVacation(LocalDate startVacation) {
        this.startVacation = startVacation;
    }

    public LocalDate getEndVacation() {
        return endVacation;
    }

    public void setEndVacation(LocalDate endVacation) {
        this.endVacation = endVacation;
    }

    public Boolean getVacation() {
        return isVacation;
    }

    public void setVacation(Boolean vacation) {
        isVacation = vacation;
    }
}
