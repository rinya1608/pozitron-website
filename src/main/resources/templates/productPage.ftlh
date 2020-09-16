<#import "parts/common.ftlh" as c>
<@c.template title="${product.name}">
<!-- productPage -->
<section class="section-outer section-productPage">
    <div class="section-inner">
        <div class="section-productPage-wrapper">
            <div class="section-productPage-wrapper-img">
                <img src="http://wordpress.templatemela.com/woo/WCM05/WCM050109/wp-content/uploads/2019/01/12-1-274x274.jpg" alt="">
            </div>
            <div class="section-productPage-wrapper-information">
                <div class="section-productPage-wrapper-information-title" ><h1 id="productName">${product.name}</h1></div>
                <div class="section-productPage-wrapper-information-category">Категория: <a>${product.category.parentCategory.name}</a></div>
                <div class="section-productPage-wrapper-information-subcategory">Подкатегория: <a>${product.category.name}</a></div>
                <div class="section-productPage-wrapper-information-inStock">В наличии: <span >${product.quantity} ${product.unit}</span></div>
                <div class="section-productPage-wrapper-information-price">
                    <#if product.priceWithDiscount??>
                        <span class="section-productPage-wrapper-information-price_old">${product.price}<i class="fal fa-ruble-sign fa-sm"></i></span>
                    </#if>
                    ${product.priceWithDiscount!product.price}<i class="fal fa-ruble-sign fa-sm"></i>

                </div>
                <div class="section-productPage-wrapper-information-description">
                    <p>
                        ${product.description}
                    </p>
                </div>
                <div class="section-productPage-wrapper-information-quantity">
                    <form method="post" id="formAddProductToCartPage">
                        <div id="button_minus" class="section-productPage-wrapper-information-quantity__minus">-</div>
                        <input id="input_number${product.id}" class="section-productPage-wrapper-information-quantity__number" type="number" name="" value="1" min="1" max="${product.quantity}">
                        <div id="button_plus" class="section-productPage-wrapper-information-quantity__plus">+</div>
                        <span id="spanProductCategoryId" style="display: none;">${product.category.id}</span>
                        <span id="spanProductId" style="display: none;">${product.id}</span>
                        <input type="hidden" id="csrf" name="_csrf" value="${_csrf.token}" />
                        <button type="submit" name="${product.id}" id="submitAddProductToCart" class="section-productPage-wrapper-information-quantity__button">В корзину</button>
                        <span id="result"></span>
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- / productPage -->
</@c.template>