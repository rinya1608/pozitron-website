<#include "parts/security.ftlh">
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,700&display=swap&subset=cyrillic" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
    <script src="https://kit.fontawesome.com/07a60b0def.js" crossorigin="anonymous"></script>
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
                                <div>
                                    <form action="/logout" method="post">
                                        <input type="hidden" name="_csrf" value="${_csrf.token}">
                                        <input type="submit" value="выйти">
                                    </form>
                                </div>
                            <#else>
                                <a href="/login">вход</a>/<a href="/registration">регистрация</a>
                        </#if>
                    </div>
                </div>
            </div>
            <div class="section-header-middle">
                <div class="section-header-middle-wrapper">
                    <div class="section-header-middle-wrapper-logo"></div>
                    <div class="section-header-middle-wrapper-search">
                        <form action="" class="section-header-middle-wrapper-search-form">
                            <input type="search" name="" placeholder="Поиск" class="section-header-middle-wrapper-search-form_input">
                            <input type="submit" value="Найти" class="section-header-middle-wrapper-search-form_button">
                        </form>
                    </div>
                    <div class="section-header-middle-wrapper-icon">
                        <span><i class="fas fa-shopping-cart"></i></span>
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
                                <#list categories as category>
                                    <li><a href="/category/${category.id}">${category.name}</a></li>
                                </#list>
                            </ul>
                        </li>
                        <li><a href="#">Главная</a></li>
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
    <section class="section-outer section-slider">
        <div class="section-inner">
            slider
        </div>
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