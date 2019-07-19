package sbscrud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import sbscrud.entity.User;
import sbscrud.form.UpdateForm;
import sbscrud.service.UserDetailsService;
import sbscrud.util.MD5Util;
import sbscrud.validation.GroupOrder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.GroupSequence;
import java.time.LocalDateTime;

@Controller
public class UpdateController implements MainHtmlAttribute {

    @GroupSequence({GroupOrder.Group1.class, GroupOrder.Group2.class, GroupOrder.Group3.class, GroupOrder.Group4.class, GroupOrder.Group5.class})
    public interface ValidationOrder {}

    @Autowired
    UserDetailsService userDetailsService;

    @PostMapping("/update")
    public String update(Model model, @Validated(ValidationOrder.class) UpdateForm updateForm, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            userAttribute(userDetailsService, model);
            return "main";
        }
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var username = auth.getName();
        var user = userDetailsService.loadUserByUsername(username);
        var newMailAddress = updateForm.getNewMailAddress();
        var newFirstName = updateForm.getNewFirstName();
        var newLastName = updateForm.getNewLastName();
        if (!newMailAddress.isBlank()) user.setMailAddress(newMailAddress);
        if (!newFirstName.isBlank()) user.setFirstName(newFirstName);
        if (!newLastName.isBlank()) user.setLastName(newLastName);
        if (!(newMailAddress.isBlank() || newFirstName.isBlank() || newLastName.isBlank())) user.setModifiedAt(LocalDateTime.now());
        try {
            userDetailsService.registerUser(user);
            return "redirect:/main";
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            model.addAttribute("updateError", true);
            return "redirect:/main";
        }
    }
    private String getAvatarUrl(User user) {
        var hash = MD5Util.md5Hex(user.getMailAddress());
        return String.format("https://www.gravatar.com/avatar/%s", hash);
    }
}
