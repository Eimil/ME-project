/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logics;

import javax.ejb.Local;

/**
 *
 * @author Magnus
 */
@Local
public interface UtilsLocal {

    public int getStoreIdByUserId(int userId);
    
}
