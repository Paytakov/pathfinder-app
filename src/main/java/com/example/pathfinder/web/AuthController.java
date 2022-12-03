package com.example.pathfinder.web;

import com.example.pathfinder.model.dto.UserRegisterDTO;
import com.example.pathfinder.model.entity.User;
import com.example.pathfinder.model.enums.UserLevelEnum;
import com.example.pathfinder.model.view.UserProfileView;
import com.example.pathfinder.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/users")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerConfirm(@Valid UserRegisterDTO userRegisterDTO,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(
                    "userRegisterDTO", userRegisterDTO);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.userRegisterDTO", bindingResult);

            return "redirect:/register";
        }

        this.authService.register(userRegisterDTO);

        return "redirect:/users/login";

    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @ModelAttribute("userRegisterDTO")
    public UserRegisterDTO initForm() {
        return new UserRegisterDTO();
    }

    @GetMapping("/profile")
    public String profile(Principal principal, Model model) {
        String username = principal.getName();
        User user = authService.getUserByUsername(username);
        UserProfileView userProfileView = new UserProfileView(
                username,
                user.getEmail(),
                user.getFullName(),
                user.getAge(),
                user.getLevel() != null ? user.getLevel().name() : UserLevelEnum.BEGINNER.name());

        model.addAttribute("user", userProfileView);
        return "profile";
    }
}
