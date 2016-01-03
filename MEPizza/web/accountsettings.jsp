<%-- 
    Document   : accountsettings
    Created on : 2015-dec-27, 22:21:06
    Author     : Emil Ejder
--%>
 <%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
   <head>
      <meta name="author" content="Magnus Kanfjäll,Emil Ejder">
      <meta name="copyright" content="ME-Pizza">
      <link href="/MEPizza/pizza/css.css" rel="stylesheet" type="text/css" />
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
      <title>ME-Pizza - Konto info</title>        
   </head>
   <body>
      <div id="Bakgrund">
         <div id="box">
            <div id="Logga">
               <div id="infobox">
                  ${infobox}
               </div>
            </div>
         </div>
         <div id="meny">
            <li><a href='ProductServlet'>Produkter</a></li> -
            <li><a href='CheckoutServlet'>Kassa</a></li> -
            <li><a href='PurchaseHistoryServlet'>Historik</a></li>
         </div>
         <div id="main_box">
            <form method="post" action="SettingsServlet">
                
                <table cellspacing="0" rowspan="0" border="0">
                     <thead>
                        <th colspan="4">Uppgifter</th>
                        

                    </thead>
                    <tr>
                        <td>Fullständigt namn :</td>
                        <td><input type="text" name="fullname" placeholder="${fullname}" minlength=5 value=""/></td>
                    </tr>
                     <tr>
                        <td>Adress : </td>
                        <td> <input type="text" name="address" placeholder="${address}" minlength=5 value=""/></td>
                    </tr>
                     <tr>
                        <td>Postnummer : </td>
                        <td> <input type="text" name="zipcode" placeholder="${zipcode}" minlength=5 maxlength="5" value=""/></td>
                    </tr>
                     <tr>
                        <td>Lösenord : </td>
                        <td> <input type="password" name="password" placeholder='Gammalt lösenord' minlength=5 value=""/></td>
                    </tr>
                     <tr>
                        <td>Nytt lösenord (1) : </td>
                        <td> <input type="password" name="passwordNew" placeholder='Nytt lösenord' minlength="5" value=""/></td>
                    </tr>
                     <tr>
                        <td>Nytt Lösenord (2) : </td>
                        <td> <input type="password" name="passwordNew2" placeholder='Nytt lösenord igen' minlength="5" value=""/></td>
                    </tr>
                      <tr>
                        <td>Email : </td>
                        <td> <input type="email" name="email" placeholder="${email}" minlength=5 value=""/></td>
                    </tr>
                      <tr>
                        <td>Telefonnummer : </td>
                        <td> <input type="tel" name="phone" placeholder="${phone}" minlength=5 value=""/></td>
                    </tr>
                </table>
                
                
                
                
            
               
             
               <input type="submit" name="changeButton" value="Ändra kontouppgifter" />
            </form>
              ${error}
              ${page}
              ${reason}
              ${result}
            <br>
         </div>
         <div id="fotter">
            <p id="nere">Utvecklad av Magnus Kanfjäll & Emil Ejder</p>
         </div>
      </div>
   </body>
</html>
