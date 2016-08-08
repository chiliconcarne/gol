package GameOfLife.example.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by raedschk on 05.08.2016.
 */
@Entity
public class Profil {
    @Id
    String username;

    int color1, color2, width, height;

    public Profil() {}

    public Profil(String username, int color1, int color2, int width, int height) {
        this.username = username;
        this.color1 = color1;
        this.color2 = color2;
        this.width = width;
        this.height = height;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getColor1() {
        return color1;
    }

    public void setColor1(int color1) {
        this.color1 = color1;
    }

    public int getColor2() {
        return color2;
    }

    public void setColor2(int color2) {
        this.color2 = color2;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
