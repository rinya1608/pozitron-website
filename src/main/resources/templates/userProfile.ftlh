<#import "parts/common.ftlh" as c>
<@c.template title="Личный кабинет">
    <!-- Profile-->
    <section class="section outer section-profile">
        <div class="section-inner">
            <div class="section-profile-data">
                <#if user.isActive()>
                    <#else>
                        <div class="section-profile-data-activateMessage">
                            <span>Нажмите для отправки кода активации на ваш e-mail адрес</span>
                            <form action="/user/profile/activate" method="POST">
                                <input type="hidden" name="id" value="${user.id}">
                                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                                <button type="submit">активировать</button>
                            </form>

                        </div>
                </#if>
                <#if validErrors??>
                    <div class="alertError">
                        <ul>

                            <#if message??><li>${message}</li></#if>
                            <#if validErrors??>
                                <#list validErrors as error>
                                    <li>${error}</li>
                                </#list>
                            </#if>

                        </ul>
                    </div>
                </#if>
                <#if emailMessage??>
                    <div class="message">
                        <ul>
                            <#if emailMessage??><li>${emailMessage}</li></#if>

                        </ul>
                    </div>
                </#if>
                <form method="POST" action="/user/profile" class="section-profile-data-form">
                    <div class="section-profile-data-form-item">
                        <input type="text" name="name" value="${user.name!}" placeholder=" ">
                        <label>Имя</label>

                    </div>
                    <div class="section-profile-data-form-item">
                        <input type="text" name="surname" value="${user.surname!}" placeholder=" ">
                        <label>Фамилия</label>

                    </div>
                    <div class="section-profile-data-form-item">
                        <input type="text" name="username" value="${user.username!}" placeholder=" ">
                        <label>Логин</label>

                    </div>
                    <div class="section-profile-data-form-item">
                        <input type="text" name="email" value="${user.email!}" placeholder=" ">
                        <label>Email</label>

                    </div>
                    <div class="section-profile-data-form-item">
                        <input type="text" name = "number" value="${user.number!}" placeholder=" ">
                        <label>Телефон</label>

                    </div>
                    <div class="section-profile-data-form-changepass"><a href="/user/profile/changePassword">сменить пароль</a></div>
                    <input type="hidden" name="_csrf" value="${_csrf.token}" />
                    <button type="submit">Сохранить</button>
                </form>
            </div>
        </div>
    </section>
    <!-- / Profile-->
</@c.template>