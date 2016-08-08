package GameOfLife.example.security;

/**
 * Created by raedschk on 08.08.2016.
 */

import GameOfLife.example.entity.Profil;
import GameOfLife.example.repository.ProfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Properties;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/register").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/profil", true)
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true);
    }

    @Autowired
    public void configureGlobal(ProfilRepository pRepo, AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(inMemoryUserDetailsManager(pRepo));
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(ProfilRepository pRepo) {
        final Properties users = new Properties();
        users.put("user","pass,ROLE_USER,enabled");
        pRepo.save(new Profil("user", 1, 2, 20, 20));
        return new InMemoryUserDetailsManager(users);
    }
}