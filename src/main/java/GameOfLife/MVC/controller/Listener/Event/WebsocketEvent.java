package GameOfLife.MVC.controller.Listener.Event;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by kulandas on 23.08.2016.
 */
public class WebsocketEvent {
    private String command;
    private UserDetails user;

    public WebsocketEvent(String command, UserDetails user) {
        this.command = command;
        this.user = user;
    }

    public String getCommand() {
        return command;
    }

    public UserDetails getUser() {
        return user;
    }
}
