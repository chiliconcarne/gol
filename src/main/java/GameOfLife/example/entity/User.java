package GameOfLife.example.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by sernowm on 04.08.2016.
 */
@Entity
@Table(schema = "public", name="User")
@Access(AccessType.FIELD)
@NamedQueries(
    @NamedQuery(name="User.findAll",query="from User u")
)
public class User implements Serializable {

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

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }
}
