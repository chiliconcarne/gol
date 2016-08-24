package GameOfLife.MVC.controller.Listener;

import GameOfLife.MVC.controller.Listener.Event.WebsocketEvent;

/**
 * Created by kulandas on 23.08.2016.
 */
public interface LobbyWebsocketListener {
    public void onAddGameOffer(WebsocketEvent event);
    public void onDeleteGameOffer(WebsocketEvent event);
    public void onGoToTheProfile(WebsocketEvent event);
    public void onReadyToPlay(WebsocketEvent event);
    public void onConnect(WebsocketEvent event);

}
