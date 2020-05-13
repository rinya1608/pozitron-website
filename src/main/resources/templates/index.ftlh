<#include "parts/security.ftlh">
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,700&display=swap&subset=cyrillic" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
    <link rel="stylesheet" href="../static/css/fontawesome/css/all.css">
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
    <script src="../static/script/dropdown.js"></script>    
    <title>Главная</title>
</head>
<body>
    <!-- Header -->
    <section class="section-outer section-header">
        <div class="section-inner">
            <div class="section-header-top">
                <div class="section-header-top-wrapper">
                    <div class="section-header-top-wrapper-numbers">
                        <div class="section-header-top-wrapper-numbers_logo"></div>
                        <div class="section-header-top-wrapper-numbers_content">+79505483013</div>
                    </div>
                    <div class="section-header-top-wrapper-right">
                        <#if user??>
                                <div id="profile" class="section-header-top-wrapper-right-profile" tabindex="1">
                                    <span>${name!} ${surname!} <i class="fal fa-angle-down"></i></span>
                                    <ul class="section-header-top-wrapper-right-profile-droplist">
                                        <li><a href="/user/profile"><i class="far fa-user"></i>Профиль</a></li>
                                        <#if isAdmin>
                                            <li id="admin" class="section-header-top-wrapper-right-profile-droplist_admin" tabindex="1">
                                                <a href="#"><i class="far fa-user-crown"></i>Админ</a>
                                                <ul id="admin-list"class="section-header-top-wrapper-right-profile-droplist_admin-list section-header-top-wrapper-right-profile-droplist">
                                                    <li><a href="/user"><i class="fal fa-users"></i>Пользователи</a></li>
                                                    <li><a href="/category/edit"><i class="fad fa-stream"></i>Категории</a></li>
                                                    <li><a href="/product/add"><i class="fal fa-boxes-alt"></i>Товары</a></li>
                                                </ul>
                                            </li>
                                            <#else>
                                        </#if>
                                        <li><a href="#"><i class="far fa-list-alt"></i>Заказы</a></li>
                                        <li>
                                            <form action="/logout" method="post">
                                                <input type="hidden" name="_csrf" value="${_csrf.token}">
                                                <i class="far fa-times-circle"></i><input type="submit" value="Выйти">
                                            </form>
                                        </li>
                                    </ul>
                                </div>
                            <#else>
                                <a href="/login">вход</a>/<a href="/registration">регистрация</a>
                        </#if>
                    </div>
                </div>
            </div>
            <div class="section-header-middle">
                <div class="section-header-middle-wrapper">
                    <a href="/"><div class="section-header-middle-wrapper-logo"></div></a>
                    <div class="section-header-middle-wrapper-search">
                        <form action="" class="section-header-middle-wrapper-search-form">
                            <input type="search" name="" placeholder="Поиск" class="section-header-middle-wrapper-search-form_input">
                            <input type="submit" value="Найти" class="section-header-middle-wrapper-search-form_button">
                        </form>
                    </div>
                    <div class="section-header-middle-wrapper-icon">
                        <span><i class="far fa-shopping-cart"></i></span>
                    </div>
                </div>
            </div>
            <div class="section-header-bottom">
                <div class="section-header-bottom-wrapper">
                    <ul class="section-header-bottom-wrapper-menu">
                        <li class="section-header-bottom-wrapper-menu-toplevel">
                            <a href="#">
                                <i class="fas fa-bars"></i>
                                <span class="section-header-bottom-wrapper-menu_category">Категории</span>
                                <i class="fas fa-angle-down"></i>
                            </a>
                            <ul class="section-header-bottom-wrapper-menu-sublevel">
                                <#list mainCategories as mainCategory>
                                    <li><a href="/category/${mainCategory.id}">${mainCategory.name}</a></li>
                                </#list>
                            </ul>
                        </li>
                        <li><a href="/">Главная</a></li>
                        <li><a href="#">Доставка</a></li>
                        <li><a href="#">О нас</a></li>
                        <li><a href="#">Контакты</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </section>
    <!-- / Header -->

    <!-- / Slider -->
    <section id="slider" class="section-outer section-slider">
        <div class="section-slider-wrapper">
            <div class="section-slider-wrapper-item" style="background: url(https://images.wallpaperscraft.ru/image/solntse_skaly_pliazh_168872_1600x900.jpg) 100% 100% no-repeat;background-size: 100% auto;">
                <div>
                    <h1>Стиралка lg 553</h1>
                    <a>Заказать сейчас</a>
                </div>
            </div>
            <div class="section-slider-wrapper-item" style="background: url(https://images.wallpaperscraft.ru/image/drakon_roga_krylia_168864_1600x900.jpg) 100% 100% no-repeat;background-size: 100% auto ;">
                <div>
                    <h1>Стиралка lg 554</h1>
                    <a>Заказать сейчас</a>
                </div>
            </div>
            <div class="section-slider-wrapper-item" style="background: url(https://images.wallpaperscraft.ru/image/chasy_vintazh_vremia_168856_1600x900.jpg) 100% 100% no-repeat;background-size: 100% auto;">
                <div>
                    <h1>Стиралка lg 555</h1>
                    <a>Заказать сейчас</a>
                </div>
            </div>
        </div>
        <a href="" class="section-slider-control section-slider-control-left"></a>
        <a href="" class="section-slider-control section-slider-control-right"></a>
        <script src="../static/script/slider.js"></script>
    </section>
    <!-- / Slider -->    
    
    <!-- Product -->
    <section class="section-outer section-product">
        <div class="section-inner">
            <div class="section-product-title">
                <div class="section-product-title-content">
                    <h1>Актуальные предложения</h1>
                </div>
            </div>
            <div class="section-product-table">
                <div class="section-product-table-card">
                        <div class="section-product-table-card-wrapper">
                            <a href="#">
                                <img src="http://wordpress.templatemela.com/woo/WCM05/WCM050109/wp-content/uploads/2019/01/12-1-274x274.jpg" alt="" class="section-product-table-card-wrapper-img"/>
                            </a>
                            <div class="section-product-table-card-wrapper-name"><a href="#">Стиралка большая</a></div>
                            <div class="section-product-table-card-wrapper-price">
                                <span>20000<i class="fas fa-ruble-sign fa-sm"></i></span>     
                                <span class="section-product-table-card-wrapper-price_old">25000<i class="fas fa-ruble-sign fa-sm"></i></span>
                            </div>  
                        </div>
                        <div class="section-product-table-card-bottom">
                            <div class="section-product-table-card-bottom-buy">
                                <i class="fas fa-cart-plus"></i>
                            </div>
                        </div>
                </div>                
            </div>
        </div>
    </section>
    <!-- / Product --> 

    <!-- Footer -->
    <section class="section-outer section-footer">
        <div class="section-inner">
            <div class="section-footer-wrapper">
                <span class="section-footer-wrapper-company">Copyright "Промбытэлектроника" Позитрон, All Rights Reserved</span>
            </div>
        </div>
    </section>
    <!-- / Footer -->

</body>
</html>