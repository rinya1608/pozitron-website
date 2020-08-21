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
            <form class="container-login-wrapper-form" action="/login" method="post">
                <span class="container-login-wrapper-form-title">Добро пожаловать</span>

                <#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
                    <div class="alertError">${Session.SPRING_SECURITY_LAST_EXCEPTION.message}</div>
                </#if>
                <div class="container-login-wrapper-form-email container-login-wrapper-form-input">
                    <input type="text" name="username" id="" placeholder="e-mail">
                </div>
                <div class="container-login-wrapper-form-password container-login-wrapper-form-input">
                    <input type="password" name="password" id="" placeholder="Пароль">
                </div>
                <div class="container-login-wrapper-form-btn">
                    <div class="container-login-wrapper-form-btn-bg"></div>
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                    <button type="submit">Войти</button>
                </div>
                <div class="container-login-wrapper-form-text">
                    <span><a href="/registration">Регестрация</a>/<a href="/recoveryPassword">Забыли пароль?</a></span>
                    ${message!""}
                </div>
            </form>     
        </div>
    </div>
</body>
</html>