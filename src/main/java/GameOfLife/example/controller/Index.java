package GameOfLife.example.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by sernowm on 04.08.2016.
 */
@Controller
public class Index implements ErrorController {
    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("message","Index");
        System.err.println("Index");
        return "main";
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
