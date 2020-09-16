<#import "parts/common.ftlh" as c>
<@c.template title="Корзина">
<!-- cartPage -->
<section class="section-outer section-cartPage">
    <div class="section-inner">
        <div class="section-cartPage-title">
            Корзина
        </div>
        <#if cart?? && (cart.orderProducts?size > 0)>
            <table class="section-cartPage-table">
                <thead class="section-cartPage-table-head">
                <tr class="section-cartPage-table-head-tr">
                    <th></th>
                    <th>название</th>
                    <th>Количество</th>
                    <th>цена</th>
                    <th></th>
                </tr>
                </thead>
                <tbody class="section-cartPage-table-body">

                <#list cart.orderProducts as orderProduct>
                    <tr id="productSection${orderProduct.id}" class="section-cartPage-table-body-tr">
                        <td><img src="http://wordpress.templatemela.com/woo/WCM05/WCM050109/wp-content/uploads/2019/01/12-1-274x274.jpg" alt=""></td>
                        <td><a href="#">${orderProduct.product.name}</a></td>
                        <td class="section-cartPage-table-body-tr-quantity">
                            <div id="quantityBlock" class="section-cartPage-table-body-tr-quantity-wrapper">
                                    <button id="button_minus${orderProduct.id}" class="section-cartPage-table-body-tr-quantity-wrapper__minus" name="${orderProduct.id}">-</button>
                                    <input id="csrf" type="hidden" name="_csrf" value="${_csrf.token}" />
                                    <input id="input_number${orderProduct.id}" class="section-cartPage-table-body-tr-quantity-wrapper__number" type="number" name="${orderProduct.id}" value="${orderProduct.getQuantity()}" min="1" max="${orderProduct.product.quantity}">
                                    <span id="result${orderProduct.id}"></span>
                                    <button id="button_plus${orderProduct.id}" class="section-cartPage-table-body-tr-quantity-wrapper__plus" name="${orderProduct.id}">+</button>
                                    <input type="submit" id="submitSaveChange${orderProduct.id}" style="display:none;">
                            </div>
                        </td>
                        <td>
                            <span>${orderProduct.product.priceWithDiscount!orderProduct.product.price!"0"}<i class="fal fa-ruble-sign fa-sm"></i></span>
                        </td>
                        <td>

<#--                            <form id="cartDelProductForm${orderProduct.id}" name="${orderProduct.id}">-->
                                <input id="csrf" type="hidden" name="_csrf" value="${_csrf.token}" />
                                <button id="submitDelProduct${orderProduct.id}" name="${orderProduct.id}"><i class="far fa-times"></i></button>
<#--                            </form>-->
                        </td>
                    </tr>
                </#list>

                </tbody>
                <tfoot class="section-cartPage-table-foot">
                <tr class="section-cartPage-table-foot-tr">
                    <td></td>
                    <td></td>
                    <td></td>
                    <th>Итог: <span id="costAllProducts">${cart.getCostAllProducts()}</span><i class="far fa-ruble-sign"></i></th>
                    <td><a href="/cart/registration">Оформить заказ</a></td>
                </tr>
                </tfoot>
            </table>
        </#if>

    </div>
</section>
<!-- / cartPage -->
</@c.template>