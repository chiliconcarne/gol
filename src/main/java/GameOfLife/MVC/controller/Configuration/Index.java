package GameOfLife.MVC.controller.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by sernowm on 04.08.2016.
 */
@Controller
@Scope("session")
public class Index implements ErrorController {

    @Autowired
    UserManager userManager;

    @RequestMapping("/testWebsocket")
    public String testWebsocket() { return "testWebsocket"; }

    @RequestMapping("/")
    public String index(Model model) {
        return "redirect:profil";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        return "login";
    }

    @RequestMapping("/lobby")
    public String lobby(Model model,
                        HttpServletRequest request
    ) {
        return "lobby";
    }

    @RequestMapping("/{side}")
    public String fallback(
            Model model,
            HttpServletRequest request
    ) {
        return "redirect:profil";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(
        @RequestParam(name = "username", required = true) String username,
        @RequestParam(name = "password", required = true) String password,
        Model model,
        HttpServletRequest request
    ) {
        return userManager.createNewUser(username, password);
    }

    @RequestMapping(value = "/profil", method = RequestMethod.GET)
    public String settings2(
            Model model,
            HttpServletRequest request
    ) {
        return "profile";
    }

    @RequestMapping(value = "/profil", method = RequestMethod.POST)
    public String settings(
            Model model,
            HttpServletRequest request
    ) {
        return "redirect:profil";
    }

    @RequestMapping("/conway")
    public String conway(Model model) {
        return "conway";
    }

    @RequestMapping("/game")
    public String game(Model model) {
        return "game";
    }

    @RequestMapping("/error")
    public String error(Model model) {
        return "404";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
