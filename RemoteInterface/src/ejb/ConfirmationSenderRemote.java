/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.ejb.Remote;

/**
 *
 * @author eimil_000
 */
@Remote
public interface ConfirmationSenderRemote {

    boolean sendConfirmation(String emailaddress, String [] data);
    
}
