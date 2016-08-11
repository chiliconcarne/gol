package GameOfLife.example.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by raedschk on 11.08.2016.
 */
@Entity
public class Offer {
    @Id
    String usernname;

    public Offer() {
    }

    public Offer(String usernname) {
        this.usernname = usernname;
    }

    public String getUsernname() {
        return usernname;
    }

    public void setUsernname(String usernname) {
        this.usernname = usernname;
    }
}
