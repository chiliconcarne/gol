package GameOfLife.example.security;

import GameOfLife.example.entity.Profil;
import GameOfLife.example.repository.ProfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.ArrayList;

/**
 * Created by raedschk on 09.08.2016.
 */
public class UserManager {

    @Autowired
    InMemoryUserDetailsManager inMemoryUserDetailsManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ProfilRepository pRepo;

    public boolean createNewUser(String username, String password)
    {
        if(!inMemoryUserDetailsManager.userExists(username))
        {
            inMemoryUserDetailsManager.createUser(new User(username, passwordEncoder.encode(password), new ArrayList<GrantedAuthority>()));
            pRepo.save(new Profil(username, 1, 2, 20, 20, 50));
            return true;
        }
        else
        {
            return false;
        }
    }
}
