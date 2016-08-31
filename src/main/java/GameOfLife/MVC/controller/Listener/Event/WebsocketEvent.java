package GameOfLife.MVC.controller.Listener.Event;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by kulandas on 23.08.2016.
 */
public class WebsocketEvent<E> {
    private String command;
    private UserDetails user;
    private E daten;
    private String room;

    public WebsocketEvent(String command, UserDetails user) {
        this.command = command;
        this.user = user;
    }

    public WebsocketEvent(String command, UserDetails user, String room) {
        this.command = command;
        this.user = user;
        this.room = room;
    }

    public WebsocketEvent(E daten, String command, UserDetails user) {
        this.command = command;
        this.user = user;
        this.daten = daten;
    }

    public WebsocketEvent(E daten, String command, UserDetails user, String room) {
        this.command = command;
        this.user = user;
        this.daten = daten;
        this.room = room;
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

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
