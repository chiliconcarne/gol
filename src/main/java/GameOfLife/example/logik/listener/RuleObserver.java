package GameOfLife.example.logik.listener;

import GameOfLife.example.logik.Cell;
import GameOfLife.example.state.CellState;
import GameOfLife.example.state.SourceState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sernowm on 12.08.2016.
 */
public class RuleObserver implements RuleListener {

    private static RuleObserver instance;

    public static RuleObserver getInstance(){
        if(instance==null)instance=new RuleObserver();
        return instance;
    }

    public static void addRuleListener(RuleListener ruleListener){
        getInstance().addListener(ruleListener);
    }

    //-----------------------------------------------------------

    private CellEvent cellEvent;

    private void addListener(RuleListener rl){
        listener.add(rl);
    }

    public void rule(Cell cell,SourceState sourseState){
        cellEvent = new CellEvent(cell,sourseState);
        if(cellEvent.getCell().getType()== CellState.neutral){

        }
    }

    List<RuleListener> listener = new ArrayList();

    @Override
    public void onRevive(CellEvent event) {
        if(cellEvent.getCell().getPlayer1()==3) {
            cellEvent.getBoard()[cellEvent.getCell().getY()][cellEvent.getCell().getY()] = cellEvent.getGame().getColorPlayer1();
            cellEvent.setTarget(SourceState.Player1);
        }
        if(cellEvent.getCell().getPlayer2()==3) {
            cellEvent.getBoard()[cellEvent.getCell().getY()][cellEvent.getCell().getY()] = cellEvent.getGame().getColorPlayer2();
            cellEvent.setTarget(SourceState.Player2);
        }
        listener.forEach(rulelistener -> rulelistener.onRevive(cellEvent));
    }

    @Override
    public void onOverPopulation(CellEvent event) {
        listener.forEach(rulelistener -> rulelistener.onOverPopulation(cellEvent));
    }

    @Override
    public void onUnderPopulation(CellEvent event) {
        listener.forEach(rulelistener -> rulelistener.onUnderPopulation(cellEvent));
    }

    @Override
    public void onAlive(CellEvent event) {
        listener.forEach(rulelistener -> rulelistener.onAlive(cellEvent));
    }

    @Override
    public void onConvert(CellEvent event) {
        listener.forEach(rulelistener -> rulelistener.onConvert(cellEvent));
    }

    @Override
    public void onAssassinate(CellEvent event) {
        listener.forEach(rulelistener -> rulelistener.onAssassinate(cellEvent));
    }
}
