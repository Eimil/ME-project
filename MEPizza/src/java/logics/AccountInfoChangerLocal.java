package logics;

import javax.ejb.Local;

/**
 *
 * @author Emil Ejder
 */
@Local
public interface AccountInfoChangerLocal {
    
    String hashString(String userParam);

    String changeUserInfo(String [] newInfo);

    String [] loadUserInfo(String previousInfo);
    
}
