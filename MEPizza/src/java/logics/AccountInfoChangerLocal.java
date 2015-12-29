package logics;

import javax.ejb.Local;

/**
 *
 * @author Emil Ejder
 */
@Local
public interface AccountInfoChangerLocal {
    
    String hashString(String userParam);

    String changeUserInfo(String [] newInfo, String userId);

    String [] loadUserInfo(String userId);

    boolean checkPassword(String password);
    
}
