package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.dao.RoleDao;
import web.model.Role;
import web.model.User;
import web.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private RoleDao roleDao;

    @Autowired
    public AdminController(UserService userService, RoleDao roleDao) {
        this.userService = userService;
        this.roleDao = roleDao;
    }

    @GetMapping("")
    public String showUserList(Model model) {
        List<User> list = userService.getAllUsers();
        model.addAttribute("listUsers", list);
        return "index";
    }

    @GetMapping(value = "/showNewUserForm")
    public String showNewUserForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);

        List<Role> list = roleDao.findAll();
        model.addAttribute("listRoles", list);
        return "newUser";
    }

    @PostMapping("/saveUser")
    public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model, @RequestParam(name = "roles", defaultValue = "") String role) {

        System.out
                .println(role);
        if (role.equals("")) return "redirect:/";

        userService.saveUser(user, role);
        return "redirect:/admin";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {

        User user = userService.getUserById(id);

        model.addAttribute("user", user);

        List<Role> list = roleDao.findAll();
        model.addAttribute("listRoles", list);
        return "updateUser";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable(value = "id") long id) {


        this.userService.deleteUserById(id);
        return "redirect:/admin";
    }
}