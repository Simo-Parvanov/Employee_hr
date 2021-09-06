package com.example.vacations.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class UserAlIInformationWebModel {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startVacation;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endVacation;
    private Boolean isVacation;
    private Long numberOfDays;
    private Long remainingDays;
    private boolean isModerator;

    public UserAlIInformationWebModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Long getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(Long numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public Long getRemainingDays() {
        return remainingDays;
    }

    public void setRemainingDays(Long remainingDays) {
        this.remainingDays = remainingDays;
    }

    public boolean getModerator() {
        return isModerator;
    }

    public void setModerator(boolean moderator) {
        isModerator = moderator;
    }
}
