package ejb;

import java.util.Random;
import javax.ejb.Remote;

/**
 *
 * @author Emil Ejder
 */
@Remote
public interface PasswordRetrievalRemote {

    String generateRandomString(Random random, String text, int length);
    
}
