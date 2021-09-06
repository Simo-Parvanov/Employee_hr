package com.example.vacations.services;

import com.example.vacations.dimain.entities.User;
import com.example.vacations.dimain.models.UserServiceModel;
import com.example.vacations.web.dto.UserAlIInformationWebModel;
import com.example.vacations.web.dto.UserRegistrationWebDto;
import javassist.NotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService extends UserDetailsService {
    UserServiceModel save(UserRegistrationWebDto userRegistrationDto);
    UserServiceModel login(String username);
    List<UserAlIInformationWebModel> getAllEmployees();
    UserAlIInformationWebModel getEmployeeById(String id) throws NotFoundException;
    UserAlIInformationWebModel getEmployeeByEmailForMyVacation(String email);
    void updateEmployee(UserAlIInformationWebModel userAlIInformationWebModel, String flag);
    void deleteEmployeeById(String id) throws NotFoundException;
    List<UserAlIInformationWebModel> getEmployeesByVacation();

}
