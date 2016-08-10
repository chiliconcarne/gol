package GameOfLife.example.entity;

import GameOfLife.example.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Id;

/**
 * Created by sernowm on 10.08.2016.
 */
public class Game {
    @Id
    int id;
    String spieler1,spieler2;

    public Game(int id, String spieler1, String spieler2) {
        this.id = id;
        this.spieler1 = spieler1;
        this.spieler2 = spieler2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpieler1() {
        return spieler1;
    }

    public void setSpieler1(String spieler1) {
        this.spieler1 = spieler1;
    }

    public String getSpieler2() {
        return spieler2;
    }

    public void setSpieler2(String spieler2) {
        this.spieler2 = spieler2;
    }
}
