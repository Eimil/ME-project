/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logics;

import javax.ejb.Local;

/**
 *
 * @author magnu_000
 */
@Local
public interface ProductListerLocal {

    public String getProductsAsHtmlRows();
    
}