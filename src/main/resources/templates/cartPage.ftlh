<#import "parts/common.ftlh" as c>
<@c.template title="Корзина">
<!-- cartPage -->
<section class="section-outer section-cartPage">
    <div class="section-inner">
        <div class="section-cartPage-title">
            Корзина
        </div>
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
                <#list orderProducts as orderProduct>
                    <tr class="section-cartPage-table-body-tr">
                        <td><img src="http://wordpress.templatemela.com/woo/WCM05/WCM050109/wp-content/uploads/2019/01/12-1-274x274.jpg" alt=""></td>
                        <td><a href="#">${orderProduct.product.name}</a></td>
                        <td class="section-cartPage-table-body-tr-quantity">
                            <div class="section-cartPage-table-body-tr-quantity-wrapper">
                                <form action="">
                                    <div id="button_minus" class="section-cartPage-table-body-tr-quantity-wrapper__minus">-</div>
                                    <input id="input_number" class="section-cartPage-table-body-tr-quantity-wrapper__number" type="number" name="" value="${orderProduct.quantity}" min="1" max="${orderProduct.product.count}">
                                    <div id="button_plus" class="section-cartPage-table-body-tr-quantity-wrapper__plus">+</div>
                                </form>
                            </div>
                        </td>
                        <td>
                            <span>${orderProduct.product.price}<i class="fal fa-ruble-sign fa-sm"></i></span>
                        </td>
                        <td><i class="far fa-times"></i></td>
                    </tr>
                </#list>

            </tbody>
            <tfoot class="section-cartPage-table-foot">
                <tr class="section-cartPage-table-foot-tr">
                    <td></td>
                    <td></td>
                    <td></td>
                    <th>Итог: 678<i class="far fa-ruble-sign"></i></th>
                    <td><input type="submit" value="Оформить заказ"></td>
                </tr>
            </tfoot>
        </table>
    </div>
</section>
<!-- / cartPage -->
</@c.template>