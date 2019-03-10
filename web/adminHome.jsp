<%-- 
    Document   : adminHome
    Created on : Mar 8, 2019, 6:14:23 PM
    Author     : DATTTSE62330
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Home Page</title>
    </head>
    <body>
        <h1>Admin home</h1>
        <form method="POST" action="AdminServlet">
            <input type="submit" name="btAction" value="Crawl"/>
        </form>
    </body>
</html>
