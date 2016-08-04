package GameOfLife.example.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by sernowm on 04.08.2016.
 */
@Controller
public class Index implements ErrorController {
    @RequestMapping("/")
    public String index(Model model){
        return "login";
    }

    @RequestMapping("/profile")
    public String profile(
            @RequestParam(name = "username", required = false, defaultValue = "Max") String username, Model model
    ){
        model.addAttribute("username",username);
        return "profile";
    }

    @RequestMapping("/conway")
    public String conway(Model model){
        return "conway";
    }

    @RequestMapping("/error")
    public String error(Model model){
        return "404";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
