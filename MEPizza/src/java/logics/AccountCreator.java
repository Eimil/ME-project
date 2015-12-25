package logics;

import java.security.MessageDigest;
import javax.ejb.Stateless;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Emil Ejder
 */
@Stateless
public class AccountCreator implements AccountCreatorLocal {

    @Override
    public String hashUsername(String username) {
        byte[] usernameDigested = null;
        try {
            MessageDigest usernameMD = MessageDigest.getInstance("MD5");
            usernameMD.update(username.getBytes("UTF-8"));
            usernameDigested = usernameMD.digest();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return DatatypeConverter.printHexBinary(usernameDigested);
    }

    @Override
    public String hashPassword(String password) {
        byte[] passwordDigested = null;
        try {
            MessageDigest usernameMD = MessageDigest.getInstance("MD5");
            usernameMD.update(password.getBytes("UTF-8"));
            passwordDigested = usernameMD.digest();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return DatatypeConverter.printHexBinary(passwordDigested);
    }
}
