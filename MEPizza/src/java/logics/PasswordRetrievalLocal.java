package logics;

import java.util.Random;
import javax.ejb.Local;

/**
 *
 * @author Emil Ejder
 */
@Local
public interface PasswordRetrievalLocal {

    public String generateRandomString(Random random, String text, int length);

    public boolean sendMailToUser(String emailaddress, String generatedPassword);

    public String hashPassword(String password);

}
