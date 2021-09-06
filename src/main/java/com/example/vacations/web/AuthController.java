package com.example.vacations.web;

import com.example.vacations.services.UserService;
import com.example.vacations.web.dto.UserRegistrationWebDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String getRegister(Model model){
        if (!model.containsAttribute("user")){
            model.addAttribute("user", new UserRegistrationWebDto());
        }
        return "registration";
    }
    @PostMapping("/registration")
    public String userRegistration(@ModelAttribute("user") UserRegistrationWebDto userRegistrationWebDto,
                                   BindingResult binding,
                                   RedirectAttributes redirectAttributes) {
        try {
            userService.save(userRegistrationWebDto);
            return "redirect:/registration?success";
        }catch (Exception ex){
            redirectAttributes.addFlashAttribute("user", userRegistrationWebDto);
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/registration";
        }
//        return "redirect:/registration?success";
    }

    @GetMapping("/login")
    public String getLogin(){
         return "login";
    }
}
