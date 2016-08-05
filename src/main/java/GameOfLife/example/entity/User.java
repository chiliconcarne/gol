package GameOfLife.example.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by sernowm on 04.08.2016.
 */
@Entity
public class User implements Serializable {

    public User(){}

    public User(int id, String name, String passwort) {
        this.id = id;
        this.name = name;
        this.passwort = passwort;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;
    private String name,passwort;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
