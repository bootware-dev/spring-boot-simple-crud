package sbscrud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import sbscrud.entity.User;
import sbscrud.form.UpdateForm;
import sbscrud.service.UserDetailsService;
import sbscrud.util.MD5Util;

@Controller
public class MainController implements MainHtmlAttribute {

    @Autowired
    private UserDetailsService userDetailsService;

    @RequestMapping("/main")
    private String main(Model model) {
        model.addAttribute("updateForm", new UpdateForm());
        userAttribute(userDetailsService, model);
        return "main";
    }

}

interface MainHtmlAttribute {

    default void userAttribute(UserDetailsService userDetailsService, Model model) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var username = auth.getName();
        var user = userDetailsService.loadUserByUsername(username);
        model.addAttribute("avatarUrl", getAvatarUrl(user));
        model.addAttribute("username", username);
        model.addAttribute("mailAddress", user.getMailAddress());
        model.addAttribute("firstName", user.getFirstName());
        model.addAttribute("lastName", user.getLastName());
        model.addAttribute("createdAt", user.getCreatedAt());
        model.addAttribute("modifiedAt", user.getModifiedAt());
        model.addAttribute("admin", user.isAdmin());
        if (user.isAdmin()) model.addAttribute("users", userDetailsService.findAllUser());
    }

    private String getAvatarUrl(User user) {
        var hash = MD5Util.md5Hex(user.getMailAddress());
        return String.format("https://www.gravatar.com/avatar/%s", hash);
    }

}
