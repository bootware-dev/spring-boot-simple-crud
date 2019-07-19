package sbscrud.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

    private String getAvatarUrl(User user) {
        var hash = MD5Util.md5Hex(user.getMailAddress());
        return String.format("https://www.gravatar.com/avatar/%s", hash);
    }

}
