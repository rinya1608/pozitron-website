<#import "parts/common.ftlh" as c>
<@c.template title="Изменить пользователя">
<!-- User edit -->
<section class="section-outer section-user-edit">
    <div class="section-inner" style="min-height: 600px;background: white;border-radius: 8px;margin-top: 20px; padding: 20px;">
        <div class="section-user-edit-wrapper" >
            <form action="/user" method="post">
                <input type="text" name="username" value="${user.username}">
                <#list roles as role>
                    <div style="margin-top:10px;">
                        <label><input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("изменено","")}>${role}</label>
                    </div>
                </#list>
                <input type="hidden" name="userId" value="${user.id}">
                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                <button type="submit" style="margin-top: 10px;">сохранить</button>
            </form>
        </div>
    </div>
</section>

<!-- / User edit -->
</@c.template>