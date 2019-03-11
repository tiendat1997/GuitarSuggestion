<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Top Guitars</title>
        <link href="https://fonts.googleapis.com/css?family=Abel" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="css/grid.css"/>
        <link rel="stylesheet" type="text/css" href="css/home.css"/>        
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
                <h1 class="title">Xếp hạng top 50 guitar theo giá</h1>
            </div>
        </header>        
        <section id="main">
            
        </section>
        <footer>
            <hr class="line"/>
            <div class="container">                
                <div class="row">                    
                    <p class="col-6 copyright-text">
                        Copyright &copy; 2017 All Rights Reserved by Tien Dat
                    </p>
                </div>
            </div>            
        </footer>
    </body>
</html>
