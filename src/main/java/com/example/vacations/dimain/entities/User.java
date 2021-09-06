package com.example.vacations.dimain.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
public class User extends BaseEntity{
    @NotBlank
    @Size(max = 20)
    private String username;

    @Column(name = "first_name",  length = 30)
    @NotBlank
    private String firstName;

    @Column(name = "last_name", length = 30)
    @NotBlank
    private String lastName;

    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @DateTimeFormat
    @Column(name = "start_vacation")
    private LocalDate startVacation;

    @Column(name = "end_vacation")
    @DateTimeFormat
    private LocalDate endVacation;

    private Boolean isVacation = false;

    public User() {
    }

    public User(String username, String firstName, String lastName, String email, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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
