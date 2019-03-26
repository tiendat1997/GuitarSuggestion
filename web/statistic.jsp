<%-- 
    Document   : analytic
    Created on : Mar 20, 2019, 8:35:24 AM
    Author     : DATTTSE62330
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Thống kê</title>
        <link rel="stylesheet" type="text/css" href="css/grid.css"/>
        <link rel="stylesheet" type="text/css" href="css/base.css"/>        
        <link rel="stylesheet" type="text/css" href="css/analytic.css"/>
    </head>
    <body>
        <header id="question">
            <%@include file="fragment/headerFragment.jsp" %>
        </header>    
        <div class="container">
            <div class="row" id="user-trend-view">                
                <!-- SEARCH TREND -->
                <div class="col-12">
                    <h2 class="title">Thống kê xu hướng tìm kiếm guitar</h2>
                    <hr/>
                </div>
            </div>
            <div class="row" id="guitar-statistic">
                <div class="col-12">
                    <h2 class="title">Số liệu thống kê guitar trên thị trường Việt Nam</h2>
                    <hr/>
                </div>
                <!-- STATISTIC BY CATEGORY  -->                          
                <!-- BY PRICE LEVEL -->                 
                <!-- BY BRANDE -->                 
            </div>            
            <div class="row"> 
                <!-- BY WOOD --> 
            </div>            
        </div>        
    </div>
    <%@include file="fragment/footerFragment.jsp" %>
    <script src="js/utils.js"></script>
    <script src="js/chart.js"></script>
    <script src="js/statistic.js"></script>
</body>
</html>
