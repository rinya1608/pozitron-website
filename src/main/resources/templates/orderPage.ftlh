<#import "parts/common.ftlh" as c>
<@c.template title="заказ">
<!-- orderPage -->

<section class="section outer section-orderPage">
    <div class="section-inner">
        <div class="section-orderPage-data">
            <h2>оформление заказа</h2>
            <#if validErrors??>
                <div class="alertError">
                    <ul>
                        <#if validErrors??>
                            <#list validErrors as error>
                                <li>${error}</li>
                            </#list>
                        </#if>
                    </ul>
                </div>
            </#if>
            <form method="POST" class="section-profile-data-form">
                <div class="section-orderPage-data-form-item">
                    <input type="text" name="name" value="${user.name!}" placeholder=" ">
                    <label>Имя</label>
                </div>
                <div class="section-orderPage-data-form-item">
                    <input type="text" name = "number" value="${user.number!}" placeholder=" ">
                    <label>Номер</label>
                </div>
                <div class="section-orderPage-data-form-item">
                    <input type="text" name = "city" placeholder=" ">
                    <label>Город</label>
                </div>
                <div class="section-orderPage-data-form-item">
                    <input type="text" name = "street" placeholder=" ">
                    <label>Улица</label>
                </div>
                <div class="section-orderPage-data-form-item">
                    <input type="text" name = "houseNumber" placeholder=" ">
                    <label>Дом</label>
                </div>
                <div class="section-orderPage-data-form-item">
                    <input type="text" name = "apartmentNumber" placeholder=" ">
                    <label>квартира</label>
                </div>
                <div class="section-orderPage-data-form-item">
                    <textarea name="comment" placeholder=" "></textarea>
                    <label>Комментарий к заказу</label>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                <button type="submit">Сохранить</button>
            </form>
        </div>
    </div>
</section>

<!-- / orderPage -->
</@c.template>