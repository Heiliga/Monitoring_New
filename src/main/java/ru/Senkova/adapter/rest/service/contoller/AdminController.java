package ru.Senkova.adapter.rest.service.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.Senkova.app.api.UserAppService;


@Controller
public class AdminController {

    @Autowired
    private UserAppService userAppService;

    @GetMapping("/admin")
    public String userList(Model model) {
        model.addAttribute("allUsers", userAppService.allUsers());
        return "admin";
    }

    @PostMapping("/admin")
    public String  deleteUser(@RequestParam(required = true, defaultValue = "" ) Long userId,
                              @RequestParam(required = true, defaultValue = "" ) String action,
                              Model model) {
        if (action.equals("delete")){
            userAppService.deleteUser(userId);
        }
        return "redirect:/admin";
    }


}
