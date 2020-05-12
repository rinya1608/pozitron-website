<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,700&display=swap&subset=cyrillic" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
    <script src="https://kit.fontawesome.com/07a60b0def.js" crossorigin="anonymous"></script>
    <title>Категории</title>
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
                            <div id="dd" class="section-header-top-wrapper-right-profile" tabindex="1">
                                <span>${name!} ${surname!} <i class="fal fa-angle-down"></i></span>
                                <ul class="section-header-top-wrapper-right-profile-droplist">
                                    <li><a href="/user/profile"><i class="far fa-user"></i>Профиль</a></li>
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
                    <div class="section-header-middle-wrapper-logo"></div>
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

    <!-- Subcategory list-->
    <section class="section-subcategorylist">
        <div class="section-inner">
            <h1>${parentCategory.name}</h1>
            <ul>
                <#list categories as subcategory>
                    <li><a href="/products/${subcategory.id}">${subcategory.name}</a></li>
                </#list>
            </ul>
        </div>
    </section>
    <!-- Subcategory list-->

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