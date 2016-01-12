<%-- 
    Document   : products
    Created on : 2016-jan-03, 21:01:53
    Author     : Emil Ejder
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="author" content="Magnus Kanfjäll,Emil Ejder">
        <meta name="copyright" content="ME-Pizza">
        <link href="/MEPizza/pizza/css.css" rel="stylesheet" type="text/css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>ME-Pizza Admin Application - Produkter</title>
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
                <form method="post" action="ProductServlet" >
                    <table rowspan="0" border="0" cellspacing="0">
                        <thead>
                        <th>Produkt</th>
                        <th>Beskrivning</th>
                        <th>Bild</th>
                        <th>Pris</th>
                        <th>Alternativ</th>
                        </thead>
                        ${products}  
                    </table>
                    <input type="text" name="name" placeholder='Produktnamn' value="" required/>
                    <br>
                    <input type="text" name="description"  placeholder='Beskrivning' value="" required />
                    <br>
                    <input type="text" name="pictUrl"  placeholder='URL till bild' value="" required />
                    <br>
                    <input type="text" name="price"  placeholder='Pris' value="" required />
                    <br>
                    <input type="submit" name="addButton" value="Lägg till produkt" />
                </form>
                ${error}
            </div>
    </body>
</html>
              
              
              
              
              
              
              
              
              
              
              
              
              
              
              
              
              
              
              
              
