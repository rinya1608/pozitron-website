<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,700&display=swap&subset=cyrillic" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/login.css">
    <script src="https://kit.fontawesome.com/07a60b0def.js" crossorigin="anonymous"></script>
    <title>Восстановление доступа</title>
</head>
<body>
    <div class="container-login">
        <div class="container-login-wrapper">
            <form class="container-login-wrapper-form" method="post">
                <span class="container-login-wrapper-form-title">Восстановление</span>
                <div class="container-login-wrapper-form-email container-login-wrapper-form-input">
                    <div>${message!""}</div>
                    <input type="text" name="email" placeholder="e-mail" required>
                </div>
                <div class="container-login-wrapper-form-btn">
                    <div class="container-login-wrapper-form-btn-bg"></div>
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                    <button type="submit">Восстановить</button>
                </div>
                <div class="container-login-wrapper-form-text">
                </div>
            </form>
        </div>
    </div>
</body>
</html>