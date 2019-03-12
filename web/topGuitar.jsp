<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Top Guitars</title>
        <link href="https://fonts.googleapis.com/css?family=Abel" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="css/grid.css"/>
        <link rel="stylesheet" type="text/css" href="css/home.css"/>        
        <link rel="stylesheet" type="text/css" href="css/topGuitar.css"/>
    </head>
    <body>
        <div id="navbar" class="navbar">
            <ul class="nav">
                <li class="nav-item">
                    <a href="/GuitarSuggestion">Trang chủ</a>                    
                </li>
                <li class="nav-item active">
                    <a href="/GuitarSuggestion/DispatchServlet?btAction=topguitar">Top guitar</a>                    
                </li>
            </ul>
        </div>   
        <header id="question">
            <div class="container">
                <h1 class="title">Xếp hạng top 20 guitar theo giá</h1>
            </div>
        </header>        
        <section id="main">
            <div class="container">
                <c:set var="list" value="${requestScope.LIST}"/>
                <c:if test="${not empty list}">                    
                    <c:import var="xslt" url="WEB-INF/xsl/html_topguitar.xsl" charEncoding="UTF-8"/>
                    <x:transform doc="${list}" xslt="${xslt}"/>
                </c:if>
            </div>            
        </section>
        <footer>
            <hr class="line"/>
            <div class="container">                
                <div class="row">                    
                    <p class="col-6 copyright-text">
                        Copyright &copy; 2019 All Rights Reserved by Tien Dat
                    </p>
                </div>
            </div>            
        </footer>
    </body>
</html>
