package GameOfLife.example;

import GameOfLife.example.entity.User;
import GameOfLife.example.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GameApplication {
	public static void main(String[] args) {
		SpringApplication.run(GameApplication.class, args);
	}
	@Bean
	public CommandLineRunner init(UserRepository repository){
		return (args) -> {
			repository.save(new User(1, "Hans", "Hans"));
			repository.save(new User(2, "Florian", "Hans"));
			repository.save(new User(3, "Maier", "Hans"));
			repository.save(new User(4, "Furry", "Hans"));
			repository.save(new User(5, "Peter", "Hans"));
			repository.save(new User(6, "Sussi", "Hans"));
		};
	}
}
