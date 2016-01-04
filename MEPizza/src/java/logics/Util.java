/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logics;

import javax.ejb.Stateless;

/**
 *
 * @author Magnus Kanfjäll
 */
@Stateless
public class Util implements UtilLocal {

    @Override
    public String translateStatus(String key, String lang) {

        switch (lang) {
            case "swe":
                switch (key) {
                    case "new":
                        return "Ny order";
                    case "finished":
                        return "Avslutad";
                    case "erased":
                        return "Makulerad";
                    default:
                        break;
                }
            default:
                return key;
        }

    }
}
