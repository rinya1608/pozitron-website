<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,700&display=swap&subset=cyrillic" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/messageStyle.css">
    <script src="https://kit.fontawesome.com/07a60b0def.js" crossorigin="anonymous"></script>
    <title>Сообщение</title>
</head>
<body>
    <div id="container" align="center">

        <div class="main">
            <div class="main_in_main">
                <div class="content">
                    <h1>Операция прошла успешно</h1>
                    <span>${message!}</span>
                    <div><a href="/">вернуться на главную</a></div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>