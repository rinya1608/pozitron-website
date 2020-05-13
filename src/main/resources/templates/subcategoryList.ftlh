<#import "parts/common.ftlh" as c>
<@c.template title="Подкатегории">

    <!-- Subcategory list-->
    <section class="section-subcategorylist">
        <div class="section-inner">
            <h1>${parentCategory.name}</h1>
            <ul>
                <#list categories as subcategory>
                    <li><a href="/products/${subcategory.id}">${subcategory.name}</a></li>
                </#list>
            </ul>
        </div>
    </section>
    <!-- Subcategory list-->
</@c.template>