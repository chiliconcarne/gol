package GameOfLife.example.entity;

import javax.persistence.*;

/**
 * Created by raedschk on 04.08.2016.
 */

@Entity
@Table(name="CONFIG")
public class Config {
    @Id
    @Column(name="ID", nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private User user;

    @Column(name = "COLOR", nullable = false, columnDefinition="Integer default '0'")
    private int color;

    @Column(name = "COLOR_2", nullable = false, columnDefinition="Integer default '1'")
    private int color2;

    @Column(name = "BOARD_HEIGHT", nullable = false, columnDefinition="Integer default '20'")
    private int boardHeight;

    @Column(name = "BOARD_WIDTH", nullable = false, columnDefinition="Integer default '20'")
    private int boardWidth;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor2() {
        return color2;
    }

    public void setColor2(int color2) {
        this.color2 = color2;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public void setBoardHeight(int boardHeight) {
        this.boardHeight = boardHeight;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public void setBoardWidth(int boardWidth) {
        this.boardWidth = boardWidth;
    }
}
