package GameOfLife.MVC.controller.Listener;

import GameOfLife.MVC.controller.Listener.Event.WebsocketEvent;

/**
 * Created by kulandas on 23.08.2016.
 */
public interface GameLobbyWebsocketListener {
    public void onStart(WebsocketEvent event);
    public void onLeave(WebsocketEvent event);
    public void onConnect(WebsocketEvent event);
    public void onSelectTeam(WebsocketEvent event);
}
