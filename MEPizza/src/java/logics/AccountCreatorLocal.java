/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logics;

import javax.ejb.Local;

/**
 *
 * @author Emil Ejder
 */
@Local
public interface AccountCreatorLocal {
    
    public String hashUsername(String username);
    
    public String hashPassword(String password);
    
}
