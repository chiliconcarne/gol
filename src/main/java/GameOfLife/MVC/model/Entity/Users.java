package GameOfLife.MVC.model.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by sernowm on 24.08.2016.
 */
@Entity
public class Users {
    @Id
    String username;
    String password;
    @Column(columnDefinition = "TINYINT(4) NOT NULL DEFAULT '1'")
    int enabled=1;

    public Users() {
    }

    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }
}
