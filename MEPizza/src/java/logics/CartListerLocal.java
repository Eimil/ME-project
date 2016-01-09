package logics;

/*
 * Local interface for the CartLister bean.
 */

import javax.ejb.Local;

/**
 *
 * @author Magnus Kanfjäll
 */
@Local
public interface CartListerLocal {

 

    public String [] cartContentAsHtmlRow(int userId);

    String getUserInfo(int userId);
    
}
