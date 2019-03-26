<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Top Guitars</title>
        <link rel="stylesheet" type="text/css" href="css/grid.css"/>             
        <link rel="stylesheet" type="text/css" href="css/base.css"/>
        <link rel="stylesheet" type="text/css" href="css/topGuitar.css"/>
    </head>
    <body>        
        <header id="question">    
            <%@include file="fragment/headerFragment.jsp" %>                

            <div id="subnav">
                <c:set var="cateId" value="${requestScope.CATE_ID}"/>
                <ul class="subnav">                    
                    <li class="subnav-item ${cateId eq 1 ? 'active' : ''}">
                        <a href="/GuitarSuggestion/DispatchServlet?btAction=topguitar&cateId=1">Acoustic</a>                    
                    </li>
                    <li class="subnav-item ${cateId eq 2 ? 'active' : ''}">
                        <a href="/GuitarSuggestion/DispatchServlet?btAction=topguitar&cateId=2">Classic</a>                    
                    </li>
                    <li class="subnav-item ${cateId eq 3 ? 'active' : ''}">
                        <a href="/GuitarSuggestion/DispatchServlet?btAction=topguitar&cateId=3">Electric</a>                    
                    </li>
                    <li class="subnav-item ${cateId eq 5 ? 'active' : ''}">
                        <a href="/GuitarSuggestion/DispatchServlet?btAction=topguitar&cateId=5">Vọng Cổ</a>                    
                    </li>
                    <li class="subnav-item ${cateId eq 4 ? 'active' : ''}">
                        <a href="/GuitarSuggestion/DispatchServlet?btAction=topguitar&cateId=4">Ukulele</a>                    
                    </li>                        
                </ul>
            </div>
            <div class="container">
                <row>
                    <h1 class="title">XẾP HẠNG 10 GUITAR TỐT NHẤT</h1>
                </row>
            </div>
        </header>        
        <section id="main">            
            <div class="container">
                <c:set var="list" value="${requestScope.LIST}"/>
                <c:if test="${empty list}">

                </c:if>
                <c:if test="${not empty list}">                    
                    <c:import var="xslt" url="WEB-INF/xsl/html_topguitar.xsl" charEncoding="UTF-8"/>
                    <x:transform doc="${list}" xslt="${xslt}"/>
                </c:if>                 
            </div>            
        </section>
        <%@include file="fragment/footerFragment.jsp" %>
        <script src="js/topGuitar.js"></script>
    </body>
</html>
