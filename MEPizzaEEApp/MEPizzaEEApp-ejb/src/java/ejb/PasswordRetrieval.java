package ejb;

import java.util.Random;
import javax.ejb.Stateless;

/**
 *
 * @author Emil Ejder
 */
@Stateless
public class PasswordRetrieval implements PasswordRetrievalRemote {

    @Override
    public String generateRandomString(Random random, String text, int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(text.charAt(random.nextInt(text.length())));
        }
        return sb.toString();
    }
}
