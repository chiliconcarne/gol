package GameOfLife.example.logik.listener;

/**
 * Created by sernowm on 12.08.2016.
 */
public  abstract class ActionRuleListenerImpl implements ActionRuleListener {
    @Override
    public void onKill(CellEvent cellEvent) {}

    @Override
    public void onPlayerRevive(CellEvent cellEvent) {}

    @Override
    public void onSpread(CellEvent cellEvent) {}
}
