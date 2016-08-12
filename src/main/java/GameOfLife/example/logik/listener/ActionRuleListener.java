package GameOfLife.example.logik.listener;

/**
 * Created by sernowm on 12.08.2016.
 */
public interface ActionRuleListener {
    void onKill(CellEvent cellEvent);
    void onPlayerRevive(CellEvent cellEvent);
    void onSpread(CellEvent cellEvent);
}
