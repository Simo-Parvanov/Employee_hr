package com.example.vacations.web;

import com.example.vacations.services.UserService;
import com.example.vacations.web.dto.UserAlIInformationWebModel;
import javassist.NotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class EmployeeController {
    public final UserService userService;

    public EmployeeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all/employees")
    public String getAllEmployees(Model model) {
        List<UserAlIInformationWebModel> list = userService.getAllEmployees();
        model.addAttribute("employees", list);
        return "list-employees";
    }

    @GetMapping("/edit/{id}")
    public String editEmployeeById(Model model, @PathVariable("id") Optional<String> id) throws NotFoundException {
        if (id.isPresent()) {
            UserAlIInformationWebModel entity = userService.getEmployeeById(id.get());
            model.addAttribute("employee", entity);
            model.addAttribute("flag", entity.getModerator());
        }
        return "add-edit-employee";
    }

    @RequestMapping(path = "editEmployee", method = RequestMethod.POST)
    public String createOrUpdateEmployee(UserAlIInformationWebModel employee,@RequestParam(value = "checkboxName", required = false) String flag)
    {
        userService.updateEmployee(employee, flag);
        return "redirect:/all/employees";
    }

    @RequestMapping(path = "/delete/{id}")
    public String deleteEmployeeById(Model model, @PathVariable("id") String id) throws NotFoundException {
        userService.deleteEmployeeById(id);
        return "redirect:/all/employees";
    }
}
