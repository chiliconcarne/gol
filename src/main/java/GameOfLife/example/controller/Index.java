package GameOfLife.example.controller;

import GameOfLife.example.entity.Profil;
import GameOfLife.example.repository.ProfilRepository;
import GameOfLife.example.security.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.context.annotation.Scope;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Created by sernowm on 04.08.2016.
 */
@Controller
@Scope("session")
public class Index implements ErrorController {

    @Autowired
    ProfilRepository pRepo;

    @Autowired
    UserManager userManager;

    @RequestMapping("/")
    public String index(Model model) {
        return "redirect:profil";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        return "login";
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
        if(userManager.createNewUser(username, password))
        {
            return "redirect:login?registered=true";
        }
        else
        {
            return "redirect:login?alreadyExists=true";
        }
    }

    @RequestMapping(value = "/profil", method = RequestMethod.GET)
    public String settings2(
            Model model,
            HttpServletRequest request
    ) {
        Profil p = pRepo.findOne(request.getRemoteUser());
        model.addAttribute("profil", p);
        return "profile";
    }

    @RequestMapping(value = "/profil", method = RequestMethod.POST)
    public String settings(
            @ModelAttribute("SpringWeb") Profil profil,
            Model model,
            HttpServletRequest request
    ) {
        profil.setUsername(request.getRemoteUser());
        pRepo.save(profil);
        return "redirect:profil";
    }

    @RequestMapping("/conway")
    public String conway(Model model) {
        return "conway";
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
