package sbscrud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import sbscrud.entity.User;
import sbscrud.form.SignupForm;
import sbscrud.service.UserDetailsService;
import sbscrud.validation.GroupOrder.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.GroupSequence;

@Controller
public class SignupController {

    @GroupSequence({Group1.class, Group2.class, Group3.class, Group4.class, Group5.class})
    public interface ValidationOrder {}

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("signupForm", new SignupForm());
        return "signup";
    }

    @PostMapping("/signup")
    public String signupPost(Model model, @Validated(ValidationOrder.class) SignupForm signupForm, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("signupForm", signupForm);
            return "signup";
        }
        var user = new User();
        user.setUsername(signupForm.getUsername());
        user.setMailAddress(signupForm.getMailAddress());
        user.setPassword(passwordEncoder.encode(signupForm.getPassword1()));
        user.setFirstName(signupForm.getFirstName());
        user.setLastName(signupForm.getLastName());
        user.setAdmin(signupForm.isAdmin());
        try {
            userDetailsService.registerUser(user);
            request.login(signupForm.getUsername(), signupForm.getPassword1());
        } catch (DataIntegrityViolationException | ServletException e) {
            e.printStackTrace();
            model.addAttribute("signupError", true);
            return "signup";
        }
        return "redirect:/main";
    }

}
