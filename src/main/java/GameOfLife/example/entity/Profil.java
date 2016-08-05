package GameOfLife.example.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by raedschk on 05.08.2016.
 */
@Entity
public class Profil {
    @Id
    int id;

    int color1, color2, width, height;

    public Profil() {}

    public Profil(User user, int color1, int color2, int width, int height) {
        this.id = user.getId();
        this.color1 = color1;
        this.color2 = color2;
        this.width = width;
        this.height = height;
    }

    public Profil(int id, int color1, int color2, int width, int height) {
        this.id = id;
        this.color1 = color1;
        this.color2 = color2;
        this.width = width;
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.id = user.getId();
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
