package GameOfLife.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@SpringBootApplication
public class GameApplication {
	@RequestMapping("/")
	public String index(Model model){
		model.addAttribute("message","Index");
		System.err.println("Index");
		return "main";
	}
	public static void main(String[] args) {
		SpringApplication.run(GameApplication.class, args);
	}
}
