package ejb;

import javax.ejb.Remote;

/**
 *
 * @author Emil Ejder
 */
@Remote
public interface AccountCreatorRemote {

    String hashUsername(String username);

    String hashPassword(String password);
    
}
