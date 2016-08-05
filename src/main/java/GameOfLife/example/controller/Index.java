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
    public String index(Model model){
        return "login";
    }

    @RequestMapping("/{side}")
    public String fallback(
            Model model,
            HttpServletRequest request
    ) {
        User u = loginCheck(request);
        if(u != null)
        {
            model.addAttribute("user", u);
            model.addAttribute("profil", pRepo.findOne(u.getId()));
            return "profile";
        }
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(
        @RequestParam(name = "username", required = true) String username,
        @RequestParam(name = "password", required = true) String password,
        @RequestParam(name = "register", required = false, defaultValue = "login") String register,
        Model model,
        HttpServletRequest request
    ){
        if(register.equals("Register")) {
            User u = uRepo.findByNameAndPasswort(username,password);
            if(u!=null) {
                model.addAttribute("message","Username schon vorhanden.");
                return "login";
            }
            uRepo.save(new User(0, username, password));
        }
        User u = uRepo.findByNameAndPasswort(username,password);
        if(u!=null) {
            request.getSession().setAttribute("u",u.getId());
            model.addAttribute("user",u);
            Profil p = pRepo.findOne(u.getId());
            model.addAttribute("profil",p);
            return "profile";
        }
        model.addAttribute("message","Fehler, User nicht vorhanden oder Daten falsch eingegeben.");
        return "login";
    }

    @RequestMapping(value = "/profil", method = RequestMethod.POST)
    public String settings(
            @ModelAttribute("SpringWeb")Profil profil,
            Model model,
            HttpServletRequest request
    ){
        User u = loginCheck(request);
        if(u != null)
        {
            profil.setUser(u);
            pRepo.save(profil);
            model.addAttribute("user",u);
            model.addAttribute("profil",profil);
            return "profile";
        }
        else
        {
            return "login";
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().removeAttribute("u");
        return "login";
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

    public User loginCheck(HttpServletRequest request){
        if(request.getSession().getLastAccessedTime()<System.currentTimeMillis()-(300000))
            request.getSession().removeAttribute("u");
        Object id = request.getSession().getAttribute("u");
        if(id!=null) {
            User u = uRepo.findOne((Integer) id);
            return u;
        }
        return null;
    }
}
