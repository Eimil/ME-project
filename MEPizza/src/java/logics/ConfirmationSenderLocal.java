package logics;

/*
 * Local interface for the ConfirmationSender bean.
 */

import javax.ejb.Local;

/**
 *
 * @author Emil Ejder
 */
@Local
public interface ConfirmationSenderLocal {
    
    public boolean sendConfirmation(String emailaddress, String[] data);
    
}
