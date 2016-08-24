package GameOfLife.MVC.controller.Listener.Event;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by kulandas on 23.08.2016.
 */
public class WebsocketEvent<E> {
    private String command;
    private UserDetails user;
    private E daten;

    public WebsocketEvent(String command, UserDetails user) {
        this.command = command;
        this.user = user;
    }

    public WebsocketEvent(E daten, String command, UserDetails user) {
        this.command = command;
        this.user = user;
        this.daten = daten;
    }

    public String getCommand() {
        return command;
    }

    public UserDetails getUser() {
        return user;
    }

    public E getDaten() {
        return daten;
    }
}
