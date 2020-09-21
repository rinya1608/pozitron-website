<#import "parts/common.ftlh" as c>
<@c.template title="${product.name}">
<!-- productPage -->
<section class="section-outer section-productPage">
    <div class="section-inner">
        <div class="section-productPage-wrapper">
            <div class="section-productPage-wrapper-img">
                <#if product.filename??>
                    <img src="/uploads/${product.filename}" alt="">
                <#else>
                    <img src="https://www.google.com/url?sa=i&url=https%3A%2F%2Fneuronsk.ru%2Fservices%2Four_destinations%2Fexamination_and_treatment%2Fpolyclinic-department.php%3Fsphrase_id%3D4075&psig=AOvVaw0cmO_PK2yiQ7m_SKxoK7xX&ust=1600691455181000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCPDh0r_e9-sCFQAAAAAdAAAAABAD" alt="">
                </#if>
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