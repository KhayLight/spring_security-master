package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import web.dao.RoleDao;
import web.model.Role;
import web.model.User;
import web.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Set;

@Controller
@RequestMapping("/")
public class RegController {

    private UserService userService;
    private RoleDao roleDao;

    @Autowired
    public RegController(UserService userService, RoleDao roleDao) {
        this.userService = userService;
        this.roleDao = roleDao;
    }

    @RequestMapping({"/"})
    public String checkAuth(Model model, HttpSession session) {

        String username = (String) session.getAttribute("username");
        String password = (String) session.getAttribute("password");
        if (username != null && password != null && username.isEmpty() == false && password.isEmpty() == false) {

            User user = userService.checkUserByUsernameAndPassword(username, password);

            Set<Role> roles = user.getRoles();
            if (roles.contains("ROLE_ADMIN")) {
                return "redirect:/admin";
            } else if (roles.contains("ROLE_USER")) {
                return "redirect:/user";
            }
        }
        return "redirect:/reg";
    }


    @RequestMapping({"/reg", "/reg.html"})
    public String reg(@CookieValue(name = "username", defaultValue = "none") String username, @CookieValue(value = "password", defaultValue = "") String password,
                      @Valid @ModelAttribute("user") User userLogin, BindingResult bindingResult, Model model) {

        if ((userLogin != null) && (userLogin.getPassword() != null) && (userLogin.getUsername() != null)) {
            userService.reg(userLogin.getUsername(), userLogin.getPassword());
            model.addAttribute("reg", true);
        } else {
            if (username.equals("") || password.equals("")) {
                model.addAttribute("reg", false);
            } else {
                userService.reg(username, password);
                model.addAttribute("reg", true);
            }

        }
        return "reg";
    }
}
