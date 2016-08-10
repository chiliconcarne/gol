package GameOfLife.example.json;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by sernowm on 10.08.2016.
 */
public class Board {
    private byte[][] board;
    private String name;
    public Board(){
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        this.name=auth.getName();
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
