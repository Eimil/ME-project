<%-- 
    Document   : checkout
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
            <li><a href='ProductServlet'>Produkter</a></li> -
            <li><a href='CheckoutServlet'>Kassa</a></li> -
            <li><a href='PurchaseHistoryServlet'>Historik</a></li>
                        </div>
                        <div id="main_box">
                            <form method="post" action="">
                            <input type="hidden" name="price" value="${price}" required>
                            <table class="customer" rowspan="0" border="0" cellspacing="0">
                                <thead>
                                    <th colspan="4">Kunduppgifter</th>
                                </thead>
                                ${userInfo}
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
                                    <td><select name='restaurant' >
                                            ${dropdown}
                                        </select> </td>
                                </tr>
                                <tr>
                                    <td>Anteckningar : </td>
                                    <td> <textarea rows="4" name="notes" cols="50" placeholder="${address}"></textarea></td>
                                </tr>
                                <tr>
                                    <td>Kortnummer : </td>
                                    <td> <input type="text" name="cardnumber" placeholder='xxxx xxxx xxxx xxxx' value="" minlength="19" maxlength="19" required/></td>
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
                                    <td> <input type="password" name="csv" placeholder='xxx'  value="" minlength=3 maxlength="3" required/></td>
                                </tr>
                                <tr>
                                    <td>Kortägare : </td>
                                    <td> <input type="text" name="cardOwner" placeholder='xxxx xxxx' minlength=5 value="" required/></td>

                            </table>
                                 <input type="submit" name="changeButton" value="Beställ" />
                            </form>
                            ${purchaseResult}
                        </div>
                        <div id="fotter">
                            <p id="nere">Utvecklad av Magnus Kanfjäll & Emil Ejder</p>
                        </div>
                    </div>
                </body>
                </html>
