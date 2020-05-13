<#import "parts/common.ftlh" as c>
<@c.template title="Добавить категории">
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
</@c.template>