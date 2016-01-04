<%-- 
    Document   : orders
    Author     : Magnus Kanfjäll
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
   <head>
      <meta name="author" content="Magnus Kanfjäll,Emil Ejder">
      <meta name="copyright" content="ME-Pizza">
      <link href="/MEPizza/pizza/css.css" rel="stylesheet" type="text/css" />
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
      <title>ME-Pizza - Tidigare köp</title>
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
                      <li><a href='OrderServlet'>Historik</a></li>
         </div>
         <div id="main_box">
                     
        <table rowspan="0" border="0" cellspacing="0">
            ${rows}
        </table>
               
         </div>
         <div id="fotter">
            <p id="nere">Utvecklad av Magnus Kanfjäll & Emil Ejder</p>
         </div>
      </div>
   </body>
</html>
