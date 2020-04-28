<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,700&display=swap&subset=cyrillic" rel="stylesheet">
    <script src="https://kit.fontawesome.com/07a60b0def.js" crossorigin="anonymous"></script>
    <title>Вход</title>
</head>
<body>
    <#list categories as category>
        <div>
            ${category.name!""}
        </div>
        <div>
            добавить подкатегорию:
            <form method="post">
                <input type="text" name="childCategoryName" placeholder="Введите название">
                <input type="hidden" name="categoryId" value="${category.id}">
                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                <button type="submit">добавить</button>
            </form>
        </div>
    </#list>
    Добавить категорию:
    <form method="post">
        <input type="text" name="categoryName" placeholder="Введите название">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button type="submit">Добавить категорию</button>
    </form>
</body>
</html>