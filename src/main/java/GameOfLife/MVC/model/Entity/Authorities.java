package GameOfLife.MVC.model.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by sernowm on 24.08.2016.
 */
@Entity
public class Authorities {
    @Id
    String username;
    String authority;

    public Authorities() {
    }

    public Authorities(String username, String authority) {
        this.username = username;
        this.authority = authority;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
