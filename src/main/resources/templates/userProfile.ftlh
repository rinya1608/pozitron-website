<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,700&display=swap&subset=cyrillic" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
    <script src="https://kit.fontawesome.com/07a60b0def.js" crossorigin="anonymous"></script>
    <title>Личный кабинет</title>
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
    <!-- Profile-->
    <section class="section outer section-profile">
        <div class="section-inner">
            <div class="section-profile-data">
                <form method="POST" class="section-profile-data-form">
                    <div class="section-profile-data-form-item">
                        <input type="text" required>
                        <label>Имя</label>
                    </div>
                    <div class="section-profile-data-form-item">
                        <input type="text" required>
                        <label>Логин</label>
                    </div>
                    <div class="section-profile-data-form-item">
                        <input type="text" required>
                        <label>Email</label>
                    </div>
                    <div class="section-profile-data-form-item">
                        <input type="text" required>
                        <label>Телефон</label>
                    </div>
                    <div class="section-profile-data-form-changepass"><a href="#">сменить пароль</a></div>
                    <button type="submit">Сохранить</button>
                </form>
            </div>
        </div>
    </section>
    <!-- / Profile-->
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