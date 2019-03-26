<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:set var="navActive" value="${requestScope.NAV_ACTIVE}"/>
<div class="header-image">
    <div class="logo-inner">
        <div id="logo">
            <img src="image/logo_transparent.png"/>
        </div>
    </div>
</div>            
<div id="navbar" class="container navbar">
    <ul class="nav">
        <li class="nav-item ${navActive eq 1 ? 'active' : ''}">
            <a href="/GuitarSuggestion">Trang chủ</a>                    
        </li>
        <li class="nav-item ${navActive eq 2 ? 'active' : ''}">
            <a href="/GuitarSuggestion/DispatchServlet?btAction=topguitar&cateId=1">Top guitar</a>                    
        </li>   
        <li class="nav-item ${navActive eq 3 ? 'active' : ''}">
            <a href="/GuitarSuggestion?btAction=statistic">Thống kê</a>                    
        </li>      
        <li class="nav-item">
            <a href="#">Bài viết</a>                    
        </li>      
        <!--                    <li class="nav-item right-side">                        
                                <img src="./image/icon/search-icon.svg"/>                        
                            </li> -->
    </ul>                
</div>          