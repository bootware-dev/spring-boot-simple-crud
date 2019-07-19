package sbscrud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import sbscrud.form.UpdateForm;
import sbscrud.service.UserDetailsService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DeleteController {

    @Autowired
    UserDetailsService userDetailsService;

    @PostMapping("/delete")
    public String update(Model model, @Validated UpdateForm updateForm, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "redirect:/main";
        }
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var username = auth.getName();
        try {
            userDetailsService.deleteUser(username);
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("deleteError", true);
            return "redirect:/main";
        }
        return "redirect:/main";
    }

}
