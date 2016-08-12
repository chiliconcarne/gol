package GameOfLife.example.logik;

import GameOfLife.example.logik.listener.CellEvent;
import GameOfLife.example.logik.listener.RuleListener;
import GameOfLife.example.logik.listener.RuleObserver;
import GameOfLife.example.state.CellState;
import org.springframework.stereotype.Controller;

/**
 * Created by sernowm on 12.08.2016.
 */
@Controller
public class PointController implements RuleListener {

    public PointController(){
        RuleObserver.addRuleListener(this);
    }

    @Override
    public void onRevive(CellEvent event) {

    }

    @Override
    public void onOverPopulation(CellEvent event) {

    }

    @Override
    public void onUnderPopulation(CellEvent event) {

    }

    @Override
    public void onAlive(CellEvent event) {

    }

    @Override
    public void onConvert(CellEvent event) {
        if(event.getTransform() == CellState.player1)
            event.getGame().addPunktePlayer1(1);
        else
            event.getGame().addPunktePlayer2(1);
    }

    @Override
    public void onAssassinate(CellEvent event) {
        if(event.getCell().getCellState() == CellState.player1)
            event.getGame().addPunktePlayer2(1);
        else
            event.getGame().addPunktePlayer1(1);
    }
}
