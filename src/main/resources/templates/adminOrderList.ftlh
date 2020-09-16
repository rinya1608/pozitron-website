<#import "parts/common.ftlh" as c>
<@c.template title="Лист заказов">
<!-- orderList -->

<section class="section-outer section-orderList">
    <div class="section-inner">
        <#if carts?size != 0>
            <#list carts as cart>
                <div id="orderBlock${cart.id}" class="section-orderList-block">
                    <h2 class="section-orderList-block-id">№${cart.id}</h2>
                    <input id="csrf" type="hidden" name="_csrf" value="${_csrf.token}" />
                    <#if cart.status.name() == "NOT_CONFIRMED">
                        <span class="section-orderList-block-status" style="color: sandybrown;">ожидает</span>
                        <button id="buttonConfirmOrder${cart.id}" name="${cart.id}" class="section-orderList-block-button">подтвердить заказ</button>
                        <button id="buttonDelOrder${cart.id}" name="${cart.id}" class="section-orderList-block-button">удалить</button>
                    <#elseif cart.status.name() == "CONFIRMED">
                        <span class="section-orderList-block-status" style="color: green;">подтвержден</span>
                        <button id="buttonCancelOrder${cart.id}" name="${cart.id}" class="section-orderList-block-button">отменить</button>
                    <#else>
                        <span class="section-orderList-block-status" style="color: red">отменен</span>
                    </#if>
                    <ul class="section-orderList-block-information">
                        <li>имя: ${cart.clientInfo.name}</li>
                        <li>адрес: город ${cart.clientInfo.city} улица ${cart.clientInfo.street} дом ${cart.clientInfo.houseNumber} квартира ${cart.clientInfo.apartmentNumber}</li>
                        <li>номер телефона: ${cart.clientInfo.number}</li>
                        <li>заказ на сумму: ${cart.getCostAllProducts()}</li>
                        <li>комментарий: ${cart.clientInfo.comment!}</li>
                    </ul>
                    <table class="section-orderList-block-table">
                        <thead class="section-orderList-block-table-head">
                        <tr class="section-orderList-block-table-head-tr">
                            <th>название</th>
                            <th>Количество</th>
                            <th>цена</th>
                        </tr>
                        </thead>
                        <tbody class="section-orderList-block-table-body">
                        <#list cart.getOrderProducts() as orderProduct>
                            <tr class="section-orderList-block-table-body-tr">
                                <td>
                                    <a href="product/${orderProduct.product.category.id}/${orderProduct.product.id}">${orderProduct.product.name}</a>
                                </td>
                                <td>${orderProduct.quantity}</td>
                                <td>${orderProduct.product.priceWithDiscount!orderProduct.product.price}</td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </#list>
        </#if>

    </div>
</section>

<!-- / orderList -->
</@c.template>