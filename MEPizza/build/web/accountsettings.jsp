<%-- 
    Document   : accountsettings
    Created on : 2015-dec-27, 22:21:06
    Author     : Emil Ejder
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
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
         </div>
         <div id="main_box">
            <form method="post" action="SettingsServlet">
               <input type="text" name="username" placeholder='Användarnamn'  value="" required/>
               <br>
               <input type="text" name="fullname" placeholder='Fullständigt namn'  value="" required/>
               <br>
               <input type="text" name="address" placeholder='Adress'  value="" required/>
               <br>
               <input type="text" name="zipcode" placeholder='Postnummer'  value="" required/>
               <br>
               <input type="password" name="password1" placeholder='Gammalt lösenord'  value="" required/>
               <br>
               <input type="password" name="password1" placeholder='Nytt lösenord'  value="" required/>
               <br>
               <input type="password" name="password2" placeholder='Nytt lösenord igen'  value="" required/>
               <br>
               <input type="text" name="email" placeholder='Email adress'  value="" required/>
               <br>
               <input type="text" name="phone" placeholder='Telefonnummer'  value="" required/>
               <br>
               <input type="submit" name="create" id ="create" value="Skapa konto" />
            </form>
              ${error}
              ${page}
              ${reason}
            <br>
         </div>
         <div id="fotter">
            <p id="nere">Utvecklad av Magnus Kanfjäll & Emil Ejder</p>
         </div>
      </div>
   </body>
</html>
