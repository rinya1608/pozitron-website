<!DOCTYPE html>
<html lang="en">
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
                                        <li id="admin" class="section-header-top-wrapper-right-profile-droplist_admin" tabindex="1">
                                            <a href="#"><i class="far fa-user-crown"></i>Админ</a>
                                            <ul id="admin-list"class="section-header-top-wrapper-right-profile-droplist_admin-list section-header-top-wrapper-right-profile-droplist">
                                                <li><a href=""><i class="fal fa-users"></i>Пользователи</a></li>
                                                <li><a href=""><i class="fad fa-stream"></i>Категории</a></li>
                                                <li><a href=""><i class="fal fa-boxes-alt"></i>Товары</a></li>
                                            </ul>
                                        </li>
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
    <!-- Category list -->
    <section class="section-outer section-admin-category">
        <div class="section-inner" style="min-height: 600px;">
            <div class="section-admin-category-add" style="background: white;padding: 20px;margin-top: 50px;border-radius: 15px;">
                <h4><i class="fas fa-plus-square" style="color: #ab0810;"></i> Добавить категорию:</h4>
                <form method="post">
                    <input type="text" name="categoryName" placeholder="Введите название" style="width: 250px;height: 30px;border-radius: 25px;border: 2px solid #153C6F;padding-left: 10px;">
                    <input type="hidden" name="_csrf" value="${_csrf.token}" />
                    <input type="submit" value="Добавить" style="height: 35px;border-radius: 12px;background:  #ab0810;;color: white;border: none;">
                </form>
            </div>
            <#list categories as category>
            <div class="section-admin-category-list" style="background: white;padding: 20px;margin-top: 10px;border-radius: 15px;">
                <div>
                    <h3>${category.name!}</h3>
                    <#list categories as childCategory>
                        <#if childCategory.parentCategory?? && childCategory.parentCategory == category>
                            <h4 style="display: block;margin-left: 40px;"><i class="fal fa-angle-right"></i>${childCategory.name!}</h4>
                            <#else>
                        </#if>
                    </#list>
                </div>
                <div>
                    <h4><i class="fas fa-plus-square" style="color: #ab0810;"></i> Добавить подкатегорию:</h4>
                    <form method="post">
                        <input type="text" name="childCategoryName" placeholder="Введите название" style="width: 150px;height: 20px;border-radius: 25px;border: 2px solid #153C6F;padding-left: 10px;">
                        <input type="hidden" name="categoryId" value="${category.id}">
                        <input type="hidden" name="_csrf" value="${_csrf.token}" />
                        <input type="submit" value="Добавить" style="height: 25px;border-radius: 8px;background:  #ab0810;;color: white;border: none;">
                    </form>
                </div>
            </div>
            </#list>
        </div>
    </section>

    <!-- Category list -->
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