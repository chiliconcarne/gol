package GameOfLife.example.controller;

import GameOfLife.example.service.BoerseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class boerseController {
    @Autowired
    private BoerseService boerse;



    @MessageMapping("/boerse/start")
    public void start(Principal principal) throws Exception {
        boerse.start(principal.getName());
    }

    @MessageMapping("/boerse/add")
    public void add(Principal principal) throws Exception {
        boerse.add(principal.getName());
    }

    @MessageMapping("/boerse/remove")
    public void remove(Principal principal) throws Exception {
        boerse.remove(principal.getName());
    }

    @MessageMapping("/boerse/accept")
    public void accept(String username, Principal principal) throws Exception {
        boerse.accept(principal.getName(),username);
    }

    @MessageMapping("/boerse/leave")
    public void leave(Principal principal) throws Exception {
        boerse.leave(principal.getName());
    }
}