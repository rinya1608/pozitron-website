<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,700&display=swap&subset=cyrillic" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
    <script src="https://kit.fontawesome.com/07a60b0def.js" crossorigin="anonymous"></script>
    <title>home</title>
</head>
<body>
<form method="post">
    <input type="text" name="productName" placeholder="название">
    <input type="text" name="description" placeholder="описание">
    <input type="text" name="price" placeholder="цена">
    <input type="text" name="count" placeholder="кол-во">
    <input type="text" name="unit" placeholder="измерение">
    <select size="5" name="categoryId">
        <#list categories as category>
            <option value="${category.id}">${category.name}</option>
        </#list>
    </select>
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <button type="submit">добавить</button>
</form>
</body>
</html>