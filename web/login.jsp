<%-- 
    Document   : login
    Created on : Mar 8, 2019, 6:13:58 PM
    Author     : DATTTSE62330
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
         <form action="LoginServlet" method="POST">
            Username: <input type="text" name="txtUsername"/>
            Password: <input type="password" name="txtPassword"/>
            <input type="submit" name="btAction" value="Login"/>
            <input type="reset" value="Reset"/>
        </form>
    </body>
</html>
