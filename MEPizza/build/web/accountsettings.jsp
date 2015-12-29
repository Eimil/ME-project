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
               Fullständigt namn : <input type="text" name="fullname" placeholder=${fullname}  value=""/>
               <br>
               Adress : <input type="text" name="address" placeholder=${address}  value=""/>
               <br>
               Postnummer : <input type="text" name="zipcode" placeholder=${zipcode}  value=""/>
               <br>
               Lösenord :<input type="password" name="password" placeholder='Gammalt lösenord'  value=""/>
               <br>
               Nytt lösenord (1) :<input type="password" name="passwordNew" placeholder='Nytt lösenord'  value=""/>
               <br>
               Nytt Lösenord (2) :<input type="password" name="passwordNew2" placeholder='Nytt lösenord igen'  value=""/>
               <br>
               Email : <input type="text" name="email" placeholder=${email}  value=""/>
               <br>
               Telefonnummer : <input type="text" name="phone" placeholder=${phone} value=""/>
               <br>
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
