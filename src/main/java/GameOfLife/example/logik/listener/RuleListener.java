package GameOfLife.example.logik.listener;

/**
 * Created by sernowm on 12.08.2016.
 */
public interface RuleListener {
    void revive(CellEvent event);
    void overPopulation(CellEvent event);
    void underPopulation(CellEvent event);
    void alive(CellEvent event);
    void convert(CellEvent event);
    void assassinate(CellEvent event);
}
