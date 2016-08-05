package GameOfLife.example.controller;

import GameOfLife.example.entity.Profil;
import GameOfLife.example.entity.User;
import GameOfLife.example.repository.ProfilRepository;
import GameOfLife.example.repository.UserRepository;
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
    UserRepository uRepo;

    @Autowired
    ProfilRepository pRepo;

    @RequestMapping("/")
    public String index(Model model) {
        return "login";
    }

    @RequestMapping("/{side}")
    public String fallback(
            Model model,
            HttpServletRequest request
    ) {
        User u = loginCheck(request);
        return "redirect:" + (u != null ? "profil" : "");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(
        @RequestParam(name = "username", required = true) String username,
        @RequestParam(name = "password", required = true) String password,
        @RequestParam(name = "register", required = false, defaultValue = "login") String register,
        Model model,
        HttpServletRequest request
    ) {
        if(register.equals("Register")) {
            User u = uRepo.findByNameAndPasswort(username,password);
            if(u != null) {
                model.addAttribute("message", "Username schon vorhanden.");
                return "login";
            } else {
                uRepo.save(new User(0, username, password));
                u = uRepo.findByNameAndPasswort(username,password);
                pRepo.save(new Profil(u.getId(), 1, 2, 20, 20));
            }
        }

        User u = uRepo.findByNameAndPasswort(username, password);
        if(u != null) {
            request.getSession().setAttribute("u", u.getId());
            return "redirect:profil";
        }
        else {
            model.addAttribute("message", "Fehler, User nicht vorhanden oder Daten falsch eingegeben.");
            return "login";
        }
    }

    @RequestMapping(value = "/profil", method = RequestMethod.GET)
    public String settings2(
            Model model,
            HttpServletRequest request
    ) {
        User u = loginCheck(request);
        if(u != null)
        {
            Profil p = pRepo.findOne(u.getId());
            model.addAttribute("user", u);
            model.addAttribute("profil", p);
            return "profile";
        }
        else
        {
            model.addAttribute("message", "Du bist nicht eingeloggt.");
            return "login";
        }
    }

    @RequestMapping(value = "/profil", method = RequestMethod.POST)
    public String settings(
            @ModelAttribute("SpringWeb") Profil profil,
            Model model,
            HttpServletRequest request
    ) {
        User u = loginCheck(request);
        if(u != null)
        {
            profil.setUser(u);
            pRepo.save(profil);
            return "redirect:profil";
        }
        else
        {
            model.addAttribute("message", "Du bist nicht eingeloggt.");
            return "login";
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, Model model) {
        request.getSession().removeAttribute("u");
        model.addAttribute("message", "Erfolgreich ausgeloggt.");
        return "redirect:login";
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

    public User loginCheck(HttpServletRequest request) {
        if(request.getSession().getLastAccessedTime()<System.currentTimeMillis() - 300000) {
            request.getSession().removeAttribute("u");
        }

        Object id = request.getSession().getAttribute("u");
        if(id != null) {
            User u = uRepo.findOne((Integer) id);
            return u;
        }
        else {
            return null;
        }
    }
}
