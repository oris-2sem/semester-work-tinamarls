package ru.itis.delivery.controllers.user;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.delivery.dto.user.RequestLoginForm;
import ru.itis.delivery.dto.user.RequestRegistrationForm;
import ru.itis.delivery.services.UserService;

import javax.validation.Valid;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Controller
@Slf4j
public class UserController {

    UserService userService;

    @GetMapping("/login")
    public String showLoginPage(Model model){
        model.addAttribute("requestLoginForm", new RequestLoginForm());
        return "security/login";
    }

    @GetMapping("/registration")
    public String showRegistrationPage(Model model){
        model.addAttribute("requestRegistrationForm", new RequestRegistrationForm());
        return "security/registration";
    }

    @PostMapping("/registration")
    public String registrationUser(@ModelAttribute("requestRegistrationForm") @Valid
                                       RequestRegistrationForm registrationForm, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "security/registration";
        }

        userService.saveClient(registrationForm);

        return "redirect:/login";
    }

}
