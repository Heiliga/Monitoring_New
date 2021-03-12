package ru.Senkova.adapter.rest.service.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.Senkova.app.api.UserAppService;


@Controller
@RequestMapping(AdminController.BASE_MAPPING)
public class AdminController {

    protected static final String BASE_MAPPING = "/admin";
    private static final String GET_USER_LIST_STRING_MAPPING = "/users";
    private static final String DELETE_USER_MAPPING = "/deleteUsers";

    @Autowired
    private UserAppService userAppService;

    @GetMapping(GET_USER_LIST_STRING_MAPPING)
    public String userList(Model model) {
        model.addAttribute("allUsers", userAppService.allUsers());
        return "admin";
    }

    @DeleteMapping(DELETE_USER_MAPPING)
    public String  deleteUser(@RequestParam(required = true, defaultValue = "" ) Long userId,
                              @RequestParam(required = true, defaultValue = "" ) String action
                              ) {
        if (action.equals("delete")){
            userAppService.deleteUser(userId);
        }
        return "redirect:/admin";
    }


}
