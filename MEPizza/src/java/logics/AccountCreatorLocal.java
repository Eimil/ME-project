package logics;

/*
 * Local interface for the AccountCreator bean.
 */

import javax.ejb.Local;

/**
 *
 * @author Emil Ejder
 */

@Local
public interface AccountCreatorLocal {

    public String hashString(String userParam);

    public boolean createAccount(String[] accountParams);

    public String checkIfCredsAvaliable(String username, String email);

}
