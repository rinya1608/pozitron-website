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
                <div class="container-login-wrapper-form-email container-login-wrapper-form-input">
                    <input type="text" name="name" id="" placeholder="Имя">
                </div>
                ${message!""}
                <div class="container-login-wrapper-form-email container-login-wrapper-form-input">
                    <input type="text" name="username" id="" placeholder="Логин">
                </div>
                <div class="container-login-wrapper-form-password container-login-wrapper-form-input">
                    <input type="password" name="password" id="" placeholder="Пароль">
                </div>
                <div class="container-login-wrapper-form-password container-login-wrapper-form-input">
                    <input type="email" name="email" id="" placeholder="Адрес эл. почты">
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