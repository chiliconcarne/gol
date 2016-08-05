package GameOfLife.example;

import GameOfLife.example.entity.Profil;
import GameOfLife.example.entity.User;
import GameOfLife.example.repository.ProfilRepository;
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
	public CommandLineRunner init(UserRepository uRepo, ProfilRepository pRepo){
		return (args) -> {
			User u = new User(1, "Hans", "Hans");
			uRepo.save(u);
			pRepo.save(new Profil(u, 1, 2, 20, 20));

			u = new User(2, "Florian", "Hans");
			uRepo.save(u);
			pRepo.save(new Profil(u, 3, 7, 30, 40));
		};
	}
}
