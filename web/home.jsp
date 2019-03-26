<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>GUITAR SUGGESTION</title>
        <link rel="stylesheet" type="text/css" href="css/grid.css"/>
        <link rel="stylesheet" type="text/css" href="css/base.css"/>
        <link rel="stylesheet" type="text/css" href="css/flexMasonry.css"/>
        <link rel="stylesheet" type="text/css" href="css/popup.css"/>
        <link rel="stylesheet" type="text/css" href="css/icon.css"/>
        <link rel="stylesheet" type="text/css" href="css/home.css"/>        
    </head>    
    <body>                
        <div id="loader" class="loader"><div></div></div>
        <div class="popup" id="popup">
            <div class="popup-inner">
                <!-- DETAIL GUITAR VIEW -->
                <div class="popup__photo">
                    <img src="" alt="">
                </div>
                <div class="popup__text">
                    <h1 class="popup-title"></h1>
                    <h1 class="popup-price"></h1>
                    <table class="popup-attribute">

                    </table>                    
                </div>                                
                <a class="popup__close" id="popup-close">X</a>
            </div>
        </div>
        <header id="question">
            <%@include file="fragment/headerFragment.jsp" %>
        </header>                
        <section id="main">
            <div class="container">
                <div class="row">
                    <div class="col-8 form-container">
                        <form action="" class="filter-form" id="filter-form">
                            <div class="form-title">
                                <h2>•  TÌM GUITAR PHÙ HỢP VỚI BẠN  •</h2>
                            </div>
                            <hr class="underline"/>
                            <ul class="questions">
                                <div class="subject">
                                    <label for="subject">Thể loại nhạc tôi thích ?</label>
                                    <select placeholder="Subject line" name="music-genre" id="subject_input" required="">                                        
                                        <option value="classic">Tôi thích nhạc cổ điển ( classic ), độc tấu</option>
                                        <option value="modern">Tôi thích nhạc trẻ, hiện đại ( acoustic ), đệm hát </option>
                                        <option value="electric">Tôi là fan của nhạc rock</option>
                                        <option value="vongco">Tôi thích vọng cổ</option>
                                    </select>
                                </div>
                                <div class="subject">
                                    <label for="subject">Guitar thùng đầy ( dáng om tròm ) hay thùng khuyết ?</label>
                                    <select placeholder="Subject line" name="body-style" id="subject_input" required="">                                        
                                        <option value="full">Thùng đầy ( âm thanh ấm và vang hơn )</option>
                                        <option value="cutaway">Thùng khuyết ( tận dụng những ngăn trên thùng , cá tính )</option>
                                    </select>
                                </div>
                                <div class="subject">
                                    <label for="subject">Tôi sẽ mua 1 cây guitar với giá ?</label>
                                    <select placeholder="Subject line" name="price-level" id="subject_input" required="">                                       
                                        <option value="low">Phổ thông ( <= 3 triệu 5 )</option>
                                        <option value="middle">Trung cấp ( > 3 triệu 5 và <= 10 triệu )</option>
                                        <option value="high">Cao cấp ( > 10 triệu )</option>
                                    </select>
                                </div>
                                <div class="subject">
                                    <label for="subject">Một thương hiệu nước ngoài hay trong nước ?</label>
                                    <select placeholder="Subject line" name="origin" id="subject_input" required="">                                        
                                        <option value="foreign">Thương hiệu nước ngoài</option>
                                        <option value="vietnam">Thương hiệu trong nước</option>                                        
                                    </select>
                                </div>
                                <div class="subject" style="text-align: center">
                                    <button type="submit" class="outline-button" >Tìm guitar</button>
                                </div>
                                <input type="hidden" name="btAction" value="recommend"/>                              
                            </ul>                                
                        </form>
                        <div class="movement">
                            <a href="#result" class="scrolldown">
                                <span></span>
                                <span></span>
                                <span></span>                                                            
                            </a>
                        </div>
                    </div>
                </div>
            </div>           
            <div class="container" id="result">                
                <div class="row result-title">
                    <h2 class="center-in">KẾT QUẢ</h2>                    
                </div>
                <hr class="underline" style="margin: 0 auto; width:94%"/>
                <div class="row" id="empty-row">                    
                </div>
                <div class="row" id="guitar-wrapper">                                        
                    <div class="masonry" id="guitar-container">
                        <!-- GUITAR LIST RESULT -->
                    </div>
                    <div class="paging" id="guitar-paging">

                    </div>
                </div>
            </div>
            <div class="movement">
                <a href="#question" class="scrolltop">
                    <span></span>
                    <span></span>
                    <span></span></br>                    
                </a>
            </div>
        </div>
    </section>
    <%@include file="fragment/footerFragment.jsp" %>
    <script src="./js/utils.js"></script>
    <script src="./js/home.js"></script>
</body>
</html>
