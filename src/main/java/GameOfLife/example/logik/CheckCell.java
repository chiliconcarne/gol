package GameOfLife.example.logik;

/**
 * Created by sernowm on 11.08.2016.
 */
public class CheckCell {
    private int neutral,spieler1,spieler2;

    public CheckCell(int neutral, int spieler1, int spieler2) {
        this.neutral = neutral;
        this.spieler1 = spieler1;
        this.spieler2 = spieler2;
    }

    public int getNeutral() {
        return neutral;
    }

    public void setNeutral(int neutral) {
        this.neutral = neutral;
    }

    public int getSpieler1() {
        return spieler1;
    }

    public void setSpieler1(int spieler1) {
        this.spieler1 = spieler1;
    }

    public int getSpieler2() {
        return spieler2;
    }

    public void setSpieler2(int spieler2) {
        this.spieler2 = spieler2;
    }
}
