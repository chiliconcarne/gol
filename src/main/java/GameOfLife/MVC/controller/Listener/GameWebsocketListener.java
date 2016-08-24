package GameOfLife.MVC.controller.Listener;

import GameOfLife.MVC.controller.Listener.Event.WebsocketEvent;

/**
 * Created by kulandas on 23.08.2016.
 */
public interface GameWebsocketListener {
    public void onReady(WebsocketEvent event);
    public void onConnect(WebsocketEvent event);
    public void onEnd(WebsocketEvent event);
    public void onClickCells(WebsocketEvent event);

}
