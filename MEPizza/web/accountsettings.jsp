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
      <title>ME-Pizza</title>        
   </head>
   <body>
      <div id="Bakgrund">
         <div id="box">
            <div id="Logga">
               <div id="infobox">
                  <h3>Utloggad</h3>
               </div>
            </div>
         </div>
         <div id="meny">
            <li><a href='login.jsp'>Logga in</a></li>
            <li><a href='SettingsServlet'>Ändra kontouppgifter</a></li>
            <li><a href='LogoutController'>Logga ut</a></li>
         </div>
         <div id="main_box">
            <form method="post" action="SettingsServlet">
                
                <table cellspacing="0" rwospan="0" border="0">
                    <tr>
                        <td>Fullständigt namn :</td>
                        <td><input type="text" name="fullname" placeholder=""  value="${fullname}"/></td>
                    </tr>
                     <tr>
                        <td>Adress : </td>
                        <td> <input type="text" name="address" placeholder=${address}  value=""/></td>
                    </tr>
                     <tr>
                        <td>Postnummer : </td>
                        <td> <input type="text" name="zipcode" placeholder=${zipcode}  value=""/></td>
                    </tr>
                     <tr>
                        <td>Lösenord : </td>
                        <td> <input type="password" name="password" placeholder='Gammalt lösenord'  value=""/></td>
                    </tr>
                     <tr>
                        <td>Nytt lösenord (1) : </td>
                        <td> <input type="password" name="passwordNew" placeholder='Nytt lösenord'  value=""/></td>
                    </tr>
                     <tr>
                        <td>Nytt Lösenord (2) : </td>
                        <td> <input type="password" name="passwordNew2" placeholder='Nytt lösenord igen'  value=""/></td>
                    </tr>
                      <tr>
                        <td>Email : </td>
                        <td> <input type="text" name="email" placeholder=${email}  value=""/></td>
                    </tr>
                      <tr>
                        <td>Telefonnummer : </td>
                        <td><input type="text" name="phone" placeholder=${phone} value=""/></td>
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
