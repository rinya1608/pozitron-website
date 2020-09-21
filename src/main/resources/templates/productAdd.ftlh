<#import "parts/common.ftlh" as c>
<@c.template title="Добавить продукт">
<form method="post" enctype="multipart/form-data">
    <input type="text" name="productName" placeholder="название" required>
    <input type="text" name="description" placeholder="описание">
    <input type="text" name="price" placeholder="цена" required>
    <input type="text" name="quantity" placeholder="кол-во" required>
    <input type="text" name="unit" placeholder="измерение" required>
    <input type="text" name="discountPercent" placeholder="скидка">
    <input type="file" name="file">
    <select size="5" name="categoryId" required>
        <#list categories as category>
            <option value="${category.id}">${category.name}</option>
        </#list>
    </select>
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <button type="submit">добавить</button>
</form>
</@c.template>