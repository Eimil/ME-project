﻿﻿<%-- 
    Document   : forgotpassword
    Author     : Emil Ejder
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
   <head>
      <meta name="author" content="Magnus Kanfjäll,Emil Ejder">
      <meta name="copyright" content="ME-Pizza">
      <link href="/MEPizza/pizza/css.css" rel="stylesheet" type="text/css" />
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
      <title>ME-Pizza - Glömt lösenord</title>
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
            <form method="post" action="RetrievePasswordServlet" >
               <input type="text" name="email" placeholder='Email adress' minlength=5 value="" required/>
               <input type="submit" name="login" value="Begär nytt lösenord" />	
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

