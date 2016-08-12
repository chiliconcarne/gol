package GameOfLife.example.logik.listener;

import GameOfLife.example.logik.Cell;
import GameOfLife.example.state.CellState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sernowm on 12.08.2016.
 */
public class RuleObserver implements RuleListener, ActionRuleListener {

    private static RuleObserver instance;

    public static RuleObserver getInstance(){
        if(instance==null)instance=new RuleObserver();
        return instance;
    }

    public static void addRuleListener(RuleListener ruleListener){
        getInstance().addListener(ruleListener);
    }

    public static void addActionRuleListener(ActionRuleListener actionRuleListener){
        getInstance().addActionListener(actionRuleListener);
    }

    //-----------------------------------------------------------

    private CellEvent cellEvent;
    private List<RuleListener> listener = new ArrayList();
    private List<ActionRuleListener> actionListener = new ArrayList();

    private void addListener(RuleListener rl){
        listener.add(rl);
    }

    private void addActionListener(ActionRuleListener rl){
        actionListener.add(rl);
    }

    /*
    Die 6 Goldenen Regeln

    1 - Unterbevölkerung
    2 - Überleben
    3 - Überbevölkerung
    4 - Bekehren
    5 - Meucheln
    6 - Beleben
     */
    public void rule(Cell cell){
        cellEvent = new CellEvent(cell);

        // 1 - Unterbevölkerung
        if(cell.getType() != CellState.neutral) {
            if((cell.getFriendly()-1) < 2){
                onUnderPopulation(cellEvent);
            }
        }

        // 2 - Überleben
        if(cell.getType() != CellState.neutral) {
            if((cell.getFriendly()-1) == 2 || (cell.getFriendly()-1) == 3){
                onAlive(cellEvent);
            }
        }

        // 3 - Überbevölkerung
        if(cell.getType() != CellState.neutral) {
            if((cell.getFriendly()-1) > 3){
                onOverPopulation(cellEvent);
            }
        }

        // 4 - Bekehren
        if(cell.getType() != CellState.neutral) {
            if(cell.getEnemy() == 3) {
                cellEvent.setTransform(cell.getType() == CellState.player1 ? CellState.player2 : CellState.player1);
                onConvert(cellEvent);
            }
        }

        // 5 - Meucheln
        if(cell.getType() != CellState.neutral) {
            if(cell.getEnemy() == 2){
                onKill(cellEvent);
            }
        }

        // 6 - Beleben
        if(cell.getType() == CellState.neutral) {
            if(cell.getPlayer1() == 3 && cell.getPlayer2() != 3) {
                cellEvent.setTransform(CellState.player1);
                onRevive(cellEvent);
            }
            if(cell.getPlayer2() == 3 && cell.getPlayer1() != 3) {
                cellEvent.setTransform(CellState.player2);
                onRevive(cellEvent);
            }
        }

    }

    @Override
    public void onKill(CellEvent event) {
        setCellToNeutral(event);
        actionListener.forEach(actionListener -> actionListener.onKill(cellEvent));
    }

    @Override
    public void onPlayerRevive(CellEvent event) {
        setCellToCellState(event);
        actionListener.forEach(actionListener -> actionListener.onPlayerRevive(cellEvent));
    }

    @Override
    public void onSpread(CellEvent event) {
        CellEvent temp;
        for(int y = -1; y < 2; y++){
            for (int x = -1; x < 2; x++){
                int dy = y + event.getCell().getY();
                int dx = x + event.getCell().getX();
                temp = new CellEvent(new Cell(event.getGame(),dx,dy));
                temp.setTransform(event.getTransform());
                toggleCellToNeutral(temp);
            }
        }
        actionListener.forEach(actionListener -> actionListener.onSpread(cellEvent));
    }

    @Override
    public void onRevive(CellEvent event) {
        setCellToCellState(event);
        listener.forEach(rulelistener -> rulelistener.onRevive(cellEvent));
    }

    @Override
    public void onOverPopulation(CellEvent event) {
        setCellToNeutral(event);
        listener.forEach(rulelistener -> rulelistener.onOverPopulation(cellEvent));
    }

    @Override
    public void onUnderPopulation(CellEvent event) {
        setCellToNeutral(event);
        listener.forEach(rulelistener -> rulelistener.onUnderPopulation(cellEvent));
    }

    @Override
    public void onAlive(CellEvent event) {
        event.setTransform(event.getCell().getType());
        setCellToCellState(event);
        listener.forEach(rulelistener -> rulelistener.onAlive(cellEvent));
    }

    @Override
    public void onConvert(CellEvent event) {
        setCellToCellState(event);
        listener.forEach(rulelistener -> rulelistener.onConvert(cellEvent));
    }

    @Override
    public void onAssassinate(CellEvent event) {
        setCellToNeutral(event);
        listener.forEach(rulelistener -> rulelistener.onAssassinate(cellEvent));
    }

    private void setCellToNeutral(CellEvent event){
        event.getGame().getNewBoard()[event.getCell().getY()][event.getCell().getX()]=0;
    }

    private void setCellToCellState(CellEvent event){
        event.getGame().getNewBoard()[event.getCell().getY()][event.getCell().getX()]=event.getGame().getColorByCellState(event.getTransform());
    }

    private void toggleCellToNeutral(CellEvent event){
        if(event.getCell().getType()==CellState.neutral)
            setCellToCellState(event);
        else
            setCellToNeutral(event);
    }
}
