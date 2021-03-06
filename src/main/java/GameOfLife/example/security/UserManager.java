package GameOfLife.example.security;

import GameOfLife.example.entity.Profil;
import GameOfLife.example.repository.ProfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * Created by raedschk on 09.08.2016.
 */
public class UserManager {

    private static Properties p = new Properties();

    static {
        boolean create = false;
        String secutiryProp = System.getProperty("user.home").concat("\\gol\\security.properties");
        FileInputStream in;
        FileWriter out;
        try {
            in = new FileInputStream(secutiryProp);
            p.load(in);
        } catch (FileNotFoundException e) {
            try {
                out = new FileWriter(secutiryProp);
                p.setProperty("passwordMinLength", "5");
                p.setProperty("passwordMaxLength", "12");
                p.setProperty("passwordAllowSpecialChars", "false");
                new File(secutiryProp).mkdirs();
                p.store(out, "Zum Ändern der Passworteinstellungen");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Autowired
    private InMemoryUserDetailsManager inMemoryUserDetailsManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ProfilRepository pRepo;

    public boolean createNewUser(String username, String password) {
        if(validatPassword(password)) {
            if (!inMemoryUserDetailsManager.userExists(username)) {
                inMemoryUserDetailsManager.createUser(new User(username, passwordEncoder.encode(password), new ArrayList<GrantedAuthority>()));
                pRepo.save(new Profil(username, 1, 2, 20, 20, 50));
                return true;
            }
        }
        return false;
    }

    private boolean validatPassword(String password) {
        return  password.length()>=Integer.valueOf(p.getProperty("passwordMinLength","5")) &&
                password.length()<=Integer.valueOf(p.getProperty("passwordMaxLength","12")) &&
                (p.getProperty("passwordAllowSpecialChars","false").equals("true") ? true : Pattern.compile("[a-zA-Z0-9]+").matcher(password).matches());
    }

    private UserDetails getUserByName(String name) {
        return inMemoryUserDetailsManager.loadUserByUsername(name);
    }
}
