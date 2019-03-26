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
        <title>Admin Home</title>
        <link rel="stylesheet" type="text/css" href="css/grid.css"/>
        <link rel="stylesheet" type="text/css" href="css/adminHome.css"/>
    </head>
    <body>        
        <div class="container nav">            
            <div class="row">
                <div class="col-8 nav-title">
                    Admin Dashboard
                </div>            
                <div class="col-4 right-nav">
                    <a href="LogoutServlet">Logout</a>
                </div>
            </div>            
        </div>
        <div class="container main">          
            <div class="row">       
                <div class="col-12">
                    <form method="POST" action="AdminServlet" class="crawl-form">
                        <input type="submit" name="btAction" value="Crawl"/>
                    </form>                  
                </div>                
            </div>            
        </div>        
    </body>
</html>
