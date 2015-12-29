<%-- 
    Document   : store
    Created on : 2015-dec-27, 19:42:22
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
            <li><a href='SettingsServlet'>Ändra kontouppgifter</a></li>
            <li><a href='LogoutController'>Logga ut</a></li>
         </div>
         <div id="main_box">
                     
        <table rowspan="0" border="0" cellspacing="0">
            <tr>
                <th>Produkt</th>
                <th>Beskrivning</th>
                <th>Bild</th>
                 <th>Pris</th>
                <th>Allternativ</th>
    
            </tr>
            ${products}
           
        </table>
                <table rowspan="0" border="0" cellspacing="0">
            <tr>
                <th>Produkt</th>
                <th>Beskrivning</th>
                <th>Pris</th>
                <th>Bild</th>
                <th>Allternativ</th>
    
            </tr>
            ${cart}
           
        </table>
         </div>
         <div id="fotter">
            <p id="nere">Utvecklad av Magnus Kanfjäll & Emil Ejder</p>
         </div>
      </div>
   </body>
</html>
