<#import "parts/common.ftlh" as c>
<@c.template title="Пользователи">
<!--  User list -->
<section class="section-outer section-user-list">
    <div class="section-inner" style="min-height: 600px;background: white;margin-top:20px;border-radius: 8px;">
        <table style="margin-left: 40px;margin-top: 30px;">
            <thead>
                <tr>
                    <th>Логин</th>
                    <th>Права</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
            <#list users as user>
                <tr>
                    <td>${user.username}</td>
                    <td><#list user.roles as role>${role}<#sep>, </#list></td>
                    <td><a href="/user/${user.id}">изменить</a></td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</section>

<!--  /User list -->
</@c.template>