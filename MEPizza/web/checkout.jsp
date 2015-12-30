<%-- 
    Document   : store
    Created on : 2015-dec-27, 19:42:22
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
                <title>ME-Pizza</title>
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
                            <li><a href='SettingsServlet'>Ändra kontouppgifter</a></li>
                             <li><a href='ProductController'>Handla</a></li>
                            <li><a href='LogoutController'>Logga ut</a></li>
                        </div>
                        <div id="main_box">
                            <table class="customer" rowspan="0" border="0" cellspacing="0">
                                <thead>
                                    <th colspan="4">Kunduppgifter</th>
                                </thead>
                                <tr>
                                    <td>Kundnamn</td>
                                    <td>Magnus Kanfjäll</td>
                                    <td>Email</td>
                                    <td>magnus@kanfjall.se</td>
                                </tr>
                                <tr>
                                    <td>Adress</td>
                                    <td>Villandsgatan 14</td>
                                    <td>Postnummer</td>
                                    <td>291 34</td>
                                </tr>
                                <tr>
                                    <td>Telefonnummer</td>
                                    <td>0768934009</td>
                                    <td></td>
                                    <td></td>
                                </tr>

                            </table>

                            <table rowspan="0" border="0" cellspacing="0">
                                <thead>
                                    <th>Produkt</th>
                                    <th>Antal</th>
                                    <th>Pris</th>


                                </thead>
                                ${cart}

                            </table>
                            <table cellspacing="0" rwospan="0" border="0">
                                <thead>
                                    <th colspan="4">Betalning och leverans</th>


                                </thead>
                                <tr>
                                    <td>Pizzeria :</td>
                                    <td><select name='resturant' >
                                            ${dropdown}
                                            
                                        </select> </td>
                                </tr>
                                <tr>
                                    <td>Anteckningar : </td>
                                    <td> <textarea rows="4" cols="50" placeholder="${address}"></textarea></td>
                                </tr>
                                <tr>
                                    <td>Kortnummer : </td>
                                    <td> <input type="text" name="zipcode" placeholder='xxxx xxxx xxxx xxxx'  value=""/></td>
                                </tr>
                                <tr>
                                    <td>Utgång : </td>
                                    <td><select name='expireMM' id='expireMM'>
                                            <option value=''>Månad</option>
                                            <option value='01'>Janaury</option>
                                            <option value='02'>February</option>
                                            <option value='03'>March</option>
                                            <option value='04'>April</option>
                                            <option value='05'>May</option>
                                            <option value='06'>June</option>
                                            <option value='07'>July</option>
                                            <option value='08'>August</option>
                                            <option value='09'>September</option>
                                            <option value='10'>October</option>
                                            <option value='11'>November</option>
                                            <option value='12'>December</option>
                                        </select> 
                                        <select name='expireYY' id='expireYY'>
                                            <option value=''>År</option>
                                            <option value='11'>2016</option>
                                            <option value='12'>2017</option>
                                            <option value='12'>2018</option>
                                        </select> </td>
                                </tr>
                                <tr>
                                    <td>CSV : </td>
                                    <td> <input type="password" name="passwordNew" placeholder='xxx'  value=""/></td>
                                </tr>
                                <tr>
                                    <td>Kortägare : </td>
                                    <td> <input type="text" name="passwordNew2" placeholder='xxxx xxxx'  value=""/></td>

                            </table>
                                 <input type="submit" name="changeButton" value="Beställ" />
                        </div>
                        <div id="fotter">
                            <p id="nere">Utvecklad av Magnus Kanfjäll & Emil Ejder</p>
                        </div>
                    </div>
                </body>
                </html>
