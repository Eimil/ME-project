package logics;

import javax.ejb.Local;

/**
 *
 * @author Emil Ejder
 */
@Local
public interface LoginCheckerLocal {
    
    public String hashString(String password);

    public String checkIfValid(String username, String password);
    
}
