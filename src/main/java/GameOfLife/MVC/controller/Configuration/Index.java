package GameOfLife.MVC.controller.Configuration;

import GameOfLife.MVC.model.Entity.Player;
import GameOfLife.MVC.model.Repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by sernowm on 04.08.2016.
 */
@Controller
public class Index {

    @Autowired
    UserManager userManager;
    @Autowired
    PlayerRepository playerRepository;

    @RequestMapping("/")
    public String index(Model model,HttpServletRequest request){
        model.addAttribute("username",request.getUserPrincipal().getName());
        return "lobby";
    }

    @RequestMapping("/lobby")
    public String lobby(Model model,HttpServletRequest request) {
        model.addAttribute("username",request.getUserPrincipal().getName());
        return "lobby";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
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

    @RequestMapping("/test")
    public String test(){
        return "WebSocketTest";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile(Model model,HttpServletRequest request){
        Player player=playerRepository.findOneByName(request.getUserPrincipal().getName());
        if(player.getAvatar()!=null)
            player.setAvatar("/img/"+player.getAvatar());
        else
            player.setAvatar("/img/warten.gif");
        model.addAttribute("profile",player);
        return "profile";
    }
}
