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
        <link rel="stylesheet" type="text/css" href="css/grid.css"/>
        <link rel="stylesheet" type="text/css" href="css/base.css"/>
        <link rel="stylesheet" type="text/css" href="css/login.css"/>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col-4 form-container">                    
                    <form action="LoginServlet" method="POST" class="form">
                        <div class="title"><h2>LOGIN</h2></div>
                        <div class="label">Username</div>
                        <div>
                            <input class="textfield" type="text" name="txtUsername"/>
                        </div>
                        <div class="label">Password</div>
                        <div>
                            <input class="textfield" type="password" name="txtPassword"/>
                        </div>
                        <div>
                            <input type="submit" name="btAction" value="Login"/>
                            <input type="reset" value="Reset"/>
                        </div>

                    </form>      
                </div>
            </div>
        </div>         
    </body>
</html>
