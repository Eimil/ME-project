package logics;

import java.security.MessageDigest;
import javax.ejb.Stateless;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Emil Ejder
 */
@Stateless
public class AccountInfoChanger implements AccountInfoChangerLocal {
    
    

    @Override
    public String hashString(String userParam) {
        byte[] usernameDigested = null;
        try {
            MessageDigest paramMD = MessageDigest.getInstance("SHA-256");
            paramMD.update(userParam.getBytes("UTF-8"));
            usernameDigested = paramMD.digest();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return DatatypeConverter.printHexBinary(usernameDigested);
    }

    @Override
    public String changeUserInfo(String [] newInfo) {
        return null;
    }

    @Override
    public String [] loadUserInfo(String previousInfo) {
        return null;
    }

}
