<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,700&display=swap&subset=cyrillic" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/login.css">
    <script src="https://kit.fontawesome.com/07a60b0def.js" crossorigin="anonymous"></script>
    <title>Вход</title>
</head>
<body>
    <div class="container-login">
        <div class="container-login-wrapper">
            <form class="container-login-wrapper-form" action="/registration" method="post">
                <span class="container-login-wrapper-form-title">Регистрация</span>

                <#if message?? || validErrors??>
                    <div class="alertError">
                        <ul>

                            <#if message??><li>${message}</li></#if>
                            <#if validErrors??>
                                <#list validErrors as error>
                                    <li>${error}</li>
                                </#list>
                            </#if>

                        </ul>
                    </div>
                </#if>

                <div class="container-login-wrapper-form-name container-login-wrapper-form-input">
                    <input type="text" name="name" id="" placeholder="Имя">
                    <#if nameError??><span>${nameError}</span></#if>
                </div>
                <div class="container-login-wrapper-form-name container-login-wrapper-form-input">
                    <input type="text" name="surname" id="" placeholder="Фамилия">
                </div>
                <div class="container-login-wrapper-form-username container-login-wrapper-form-input">
                    <input type="text" name="username" id="" placeholder="Логин">
                </div>
                <#if usernameError??><span>${usernameError}</span></#if>
                <div class="container-login-wrapper-form-password container-login-wrapper-form-input">
                    <input type="password" name="password" id="" placeholder="Пароль">
                </div>
                <div class="container-login-wrapper-form-email container-login-wrapper-form-input">
                    <input type="email" name="email" id="" placeholder="Адрес эл. почты">
                </div>
                <div class="container-login-wrapper-form-number container-login-wrapper-form-input">
                    <input type="text" name="number" id="" placeholder="Номер телефона">
                </div>
                <div class="container-login-wrapper-form-btn">
                    <div class="container-login-wrapper-form-btn-bg"></div>
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                    <button type="submit">Зарегистрироваться</button>
                </div>
                <div class="container-login-wrapper-form-text">
                    <span><a href="/login">Войти в личный кабинет</a></span>
                </div>
            </form>     
        </div>
    </div>
</body>
</html>