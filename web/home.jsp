<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>GUITAR SUGGESTION</title>
        <link href="https://fonts.googleapis.com/css?family=Abel" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="css/grid.css"/>
        <link rel="stylesheet" type="text/css" href="css/flexMasonry.css"/>
        <link rel="stylesheet" type="text/css" href="css/popup.css"/>
        <link rel="stylesheet" type="text/css" href="css/home.css"/>        
    </head>
    <body>                
        <script type="text/javascript">
            function closePopup() {
                let popup = document.getElementById('popup');
                console.log(popup);
                popup.style.visibility = 'hidden';
                popup.style.opacity = 0;
            }
        </script>
        <div class="popup" id="popup">
            <div class="popup-inner">
                <div class="popup__photo">
                    <img src="https://images.unsplash.com/photo-1515224526905-51c7d77c7bb8?ixlib=rb-0.3.5&amp;s=9980646201037d28700d826b1bd096c4&amp;auto=format&amp;fit=crop&amp;w=700&amp;q=80" alt="">
                </div>
                <div class="popup__text">
                    <h1>Lorem ipsum dolor sit amet</h1>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer ex velit, viverra non vulputate vitae, blandit vitae nisl. Nullam fermentum orci et erat viverra bibendum. Aliquam sed varius nibh, vitae mattis purus. Mauris elementum sapien non ullamcorper vulputate. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Sed eget felis sit amet eros viverra pulvinar.</p>
                </div>
                <button class="popup__close" onclick="closePopup()">X</button>
            </div>
        </div>
        <header id="question">
            <div class="header-image">
                <div class="logo-inner">
                    <div id="logo">
                        <img src="image/logo/logo.png" height="200"/>
                    </div>
                </div>
            </div>
        </header>                
        <section id="main">
            <div class="container">
                <div class="row">
                    <div class="col-8 form-container">
                        <form action="" class="filter-form" id="filter-form">
                            <ul class="questions">
                                <li>
                                    <p>Bạn sẽ chọn loại hình nào nếu mua 1 cây guitar ?</p>
                                    <div class="question-options">
                                        <input type="radio" name="music-genre" value="classic" required checked/>
                                        <label>Cổ điển, chơi độc tấu</label>
                                    </div>
                                    <div class="question-options">
                                        <input type="radio" name="music-genre" value="modern"/>
                                        <label>Chơi đệm hát, fingerstyle</label>
                                    </div>
                                    <div class="question-options">
                                        <input type="radio" name="music-genre" value="electric"/>
                                        <label>Chơi rock</label>
                                    </div>                                    
                                    <div class="question-options">
                                        <input type="radio" name="music-genre" value="vongco"/>
                                        <label>Vọng cổ</label>
                                    </div>                                    
                                </li>                                
                                <li>
                                    <p>Về thùng đàn , bạn sẽ chọn 1 cây có thùng đầy hay thùng khuyết ?</p>
                                    <div class="question-options">
                                        <input type="radio" name="body-style" value="full" required checked/>
                                        <label>Thùng đầy ( âm thanh ấm và vang hơn )</label>
                                    </div>
                                    <div class="question-options">
                                        <input type="radio" name="body-style" value="cutaway"/>
                                        <label>Thùng khuyết ( tận dụng những ngăn trên thùng , cá tính )</label>
                                    </div>                                 
                                </li>
                                <li>
                                    <p>Bạn muốn mua guitar với mức giá như thế nào ?</p>
                                    <div class="question-options">
                                        <input type="radio" name="price-level" value="low" required checked/>
                                        <label>Giá rẻ ( <= 1 triệu 5 )</label>
                                    </div>
                                    <div class="question-options">
                                        <input type="radio" name="price-level" value="middle"/>
                                        <label>Trung cấp ( <= 5 triệu ) </label>
                                    </div>
                                    <div class="question-options">
                                        <input type="radio" name="price-level" value="high"/>
                                        <label>Cao cấp ( > 5 triệu )</label>
                                    </div>       
                                </li>         
                                <li>
                                    <p>Bạn muốn chọn guitar trong nước hay nước ngoài ?</p>
                                    <div class="question-options">
                                        <input type="radio" name="origin" value="vietnam" required checked/>
                                        <label>Trong nước</label>
                                    </div>
                                    <div class="question-options">
                                        <input type="radio" name="origin" value="foreign"/>
                                        <label>Nước ngoài</label>
                                    </div>                                 
                                </li>  
                            </ul>    
                            <input type="hidden" name="btAction" value="recommend"/>
                            <button type="submit" class="outline-button" >Tìm guitar</button>
                        </form>
                        <div class="movement">
                            <a href="#result">Kết quả</a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="container" id="result">                
                <div class="row">
                    <h2 class="center-in">KẾT QUẢ</h2>
                </div>
                <hr/>               
                <div class="row">                    
                    <div class="masonry" id="guitar-container">
                        <!-- GUITAR LIST RESULT -->
                    </div>
                    <div class="paging" id="guitar-paging">

                    </div>
                </div>
            </div>
            <div class="movement">
                <a href="#question">Tìm kiếm</a>
            </div>
        </div>
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
    <script src="./js/utils.js"></script>
    <script src="./js/home.js"></script>
</body>
</html>
