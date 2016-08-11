package GameOfLife.example.logik;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

/**
 * Created by sernowm on 11.08.2016.
 */
@Controller
public class LogikController {
    @Bean
    public BoardLogik facesBoardLogik(){
        return new BoardLogik();
    }
}
