package GameOfLife.example.security;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.*;
import java.util.Properties;
import java.util.regex.Pattern;

import static com.sun.xml.internal.ws.dump.LoggingDumpTube.Position.After;
import static com.sun.xml.internal.ws.dump.LoggingDumpTube.Position.Before;
import static org.junit.Assert.*;


public class UserManagerTest {

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

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void createNewUser() throws Exception {
        // Zu kurz
        assertTrue(validatPassword("12345"));
        assertFalse(validatPassword("1234"));

        // Sonderzeichen
        assertTrue(validatPassword("Maximilian"));
        assertFalse(validatPassword("Max Kruse"));

        // Zahlen / Groß- / Kleinbuchstaben
        assertTrue(validatPassword("Skr12P45"));
        assertFalse(validatPassword("Skr!Pto201K9"));

        // Zu Lang
        assertTrue(validatPassword("JOCHIELCO35F"));
        assertFalse(validatPassword("JOCHIELCO35F3"));
    }

    private boolean validatPassword(String password) {
        return  password.length()>=Integer.valueOf(p.getProperty("passwordMinLength","5")) &&
                password.length()<=Integer.valueOf(p.getProperty("passwordMaxLength","12")) &&
                (p.getProperty("passwordAllowSpecialChars","false").equals("true") ? true : Pattern.compile("[a-zA-Z0-9]+").matcher(password).matches());
    }

}