package GameOfLife.example.controller;

import GameOfLife.example.entity.User;
import GameOfLife.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    UserRepository repository;

    @RequestMapping("/")
    public String index(Model model){
        return "login";
    }

    @RequestMapping("/{side}")
    public String fallback(
            Model model,
            HttpServletRequest request
    ) {
        return loginCheck(request,model);
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
            User u = repository.findByNameAndPasswort(username,password);
            if(u!=null) {
                model.addAttribute("message","Username schon vorhanden.");
                return "login";
            }
            repository.save(new User(0, username, password));
        }
        User u = repository.findByNameAndPasswort(username,password);
        if(u!=null) {
            request.getSession().setAttribute("u",u.getId());
            model.addAttribute("user",u);
            return "profile";
        }
        model.addAttribute("message","Fehler, User nicht vorhanden oder Daten falsch eingegeben.");
        return "login";
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

    public String loginCheck(HttpServletRequest request,Model model){
        if(request.getSession().getLastAccessedTime()<System.currentTimeMillis()-(300000))
            request.getSession().removeAttribute("u");
        Object id = request.getSession().getAttribute("u");
        if(id!=null) {
            User u = repository.findOne((Integer) id);
            model.addAttribute("user", u);
            return "profile";
        }
        return "login";
    }
}
