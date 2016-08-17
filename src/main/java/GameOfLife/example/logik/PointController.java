package GameOfLife.example.logik;

import GameOfLife.example.logik.listener.CellEvent;
import GameOfLife.example.logik.listener.RuleListenerImpl;
import GameOfLife.example.logik.listener.RuleObserver;
import org.springframework.stereotype.Controller;

/**
 * Created by sernowm on 12.08.2016.
 */
@Controller
public class PointController extends RuleListenerImpl {

    public PointController(){
        RuleObserver.addRuleListener(this);
    }

    @Override
    public void onConvert(CellEvent event) {
        event.getGame().getPlayerByCellState(event.getTransform()).addPoints(1);
    }

    @Override
    public void onAssassinate(CellEvent event) {
        event.getGame().getPlayerByCellState(event.getCell().getCellState()).getOpponent(event.getGame()).addPoints(1);
    }
}
