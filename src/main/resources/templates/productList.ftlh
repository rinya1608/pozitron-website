<#import "parts/pager.ftlh" as p>
<#import "parts/common.ftlh" as c>
<@c.template title="Товары">
    <!-- Product list-->
    <section class="section-outer section-product">
        <div class="section-inner">
            <div class="section-product-title">
                <div class="section-product-title-content">
                </div>
            </div>
            <div class="section-product-table">

                <#list page.content as product>
                    <form class="section-product-table-card" method="post" id="formAddProductToCartList${product.id}" name="${product.id}">
                        <div class="section-product-table-card-wrapper">
                            <a class="section-product-table-card-wrapper-imageLink" href="/product/${category.id}/${product.id}">
                                <#if product.discountPercent??>
                                    <div class="section-product-table-card-wrapper-imageLink-sale">-${product.discountPercent}%</div>
                                    <#else>
                                </#if>
                                <#if product.filename??>
                                    <img src="/uploads/${product.filename}" alt="" class="section-product-table-card-wrapper-imageLink-img">
                                    <#else>
                                        <img src="https://www.google.com/url?sa=i&url=https%3A%2F%2Fneuronsk.ru%2Fservices%2Four_destinations%2Fexamination_and_treatment%2Fpolyclinic-department.php%3Fsphrase_id%3D4075&psig=AOvVaw0cmO_PK2yiQ7m_SKxoK7xX&ust=1600691455181000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCPDh0r_e9-sCFQAAAAAdAAAAABAD" alt="" class="section-product-table-card-wrapper-imageLink-img">
                                </#if>
                            </a>
                            <div class="section-product-table-card-wrapper-name"><a href="#" id="productName${product.id}">${product.name}</a></div>
                            <div class="section-product-table-card-wrapper-price">
                                <span>${product.priceWithDiscount!product.price}<i class="fas fa-ruble-sign fa-sm"></i></span>
                                <#if product.discountPercent??>
                                    <span class="section-product-table-card-wrapper-price_old">${product.price}<i class="fas fa-ruble-sign fa-sm"></i></span>
                                <#else>
                                </#if>
                            </div>
                        </div>
                        <div class="section-product-table-card-bottom">

                                <input type="hidden" id="csrf" name="_csrf" value="${_csrf.token}" />
                                <input id="input_number${product.id}" type="hidden" value="1">
                                <span id="result${product.id}"></span>
                                <button type="submit" name="${product.id}" id="submitAddProductToCart" class="section-product-table-card-bottom-buy">Купить</button>
                                <span id="spanProductCategoryId" style="display: none;">${product.category.id}</span>
                        </div>
                    </form>

                </#list>
            </div>

        </div>
    </section>
    <!-- / Product list--> 
    <!-- Page -->
    <@p.pager (url+category.id) page/>
    <!-- / Page -->
</@c.template>