package GameOfLife.example;

import GameOfLife.example.entity.Profil;
import GameOfLife.example.repository.ProfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@SpringBootApplication
public class GameApplication {
	public static void main(String[] args) {
		SpringApplication.run(GameApplication.class, args);
	}


	/*
	@Bean
	public CommandLineRunner init(ProfilRepository pRepo, AuthenticationManagerBuilder auth){
		return (args) -> {
			auth.inMemoryAuthentication().withUser("Hans").password("Hans").roles("USER");
			pRepo.save(new Profil("Hans", 1, 2, 20, 20));

			auth.inMemoryAuthentication().withUser("Florian").password("Hans").roles("USER");
			pRepo.save(new Profil("Florian", 3, 7, 30, 40));
		};
	}
	*/
}
