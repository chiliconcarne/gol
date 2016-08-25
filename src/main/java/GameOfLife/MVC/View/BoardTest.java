package GameOfLife.MVC.View;

import GameOfLife.MVC.controller.Controller.WebsocketController;
import GameOfLife.MVC.controller.Listener.Event.WebsocketEvent;
import GameOfLife.MVC.controller.Listener.LobbyWebsocketAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by sernowm on 25.08.2016.
 */
@Service
public class BoardTest extends LobbyWebsocketAdapter {

    private WebsocketController websocketController;

    private String board;

    @Autowired
    public BoardTest(WebsocketController websocketController) {
        this.board = "start";
        this.websocketController = websocketController;
        websocketController.addLobbyWebsocketListener(this);
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    @Override
    public void onReadyToPlay(WebsocketEvent event) {
        this.board += "abc";
        websocketController.send("/topic/msg",this.board);
    }
}
