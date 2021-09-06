package com.example.vacations.web;

import com.example.vacations.services.UserService;
import com.example.vacations.web.dto.UserAlIInformationWebModel;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.websocket.Session;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class VacationController {
    private final UserService userService;

    public VacationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/vacation")
    public String getAllEmployeesByVacation(Model model){
        List<UserAlIInformationWebModel> list = userService.getEmployeesByVacation();
        model.addAttribute("employees", list);
        return "employees_by_vacation";
    }

    @GetMapping("/my/vacation")
    public String editEmployeeById(Principal principal, Model model){
        UserAlIInformationWebModel entity = userService.getEmployeeByEmailForMyVacation(principal.getName());
        model.addAttribute("employee", entity);
        return "my-vacation";
    }
}
