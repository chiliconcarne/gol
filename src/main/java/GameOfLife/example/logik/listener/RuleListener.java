package GameOfLife.example.logik.listener;

/**
 * Created by sernowm on 12.08.2016.
 */
public interface RuleListener {
    void onRevive(CellEvent event);
    void onOverPopulation(CellEvent event);
    void onUnderPopulation(CellEvent event);
    void onAlive(CellEvent event);
    void onConvert(CellEvent event);
    void onAssassinate(CellEvent event);
}
