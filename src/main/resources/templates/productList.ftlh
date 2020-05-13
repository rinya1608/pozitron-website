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
                    <form class="section-product-table-card">
                        <div class="section-product-table-card-wrapper">
                            <a class="section-product-table-card-wrapper-imageLink" href="#">
                                <#if product.discount??>
                                    <div class="section-product-table-card-wrapper-imageLink-sale">-${product.discount}%</div>
                                    <#else>
                                </#if>
                                <img src="http://wordpress.templatemela.com/woo/WCM05/WCM050109/wp-content/uploads/2019/01/12-1-274x274.jpg" alt="" class="section-product-table-card-wrapper-imageLink-img">
                            </a>
                            <div class="section-product-table-card-wrapper-name"><a href="#">${product.name}</a></div>
                            <div class="section-product-table-card-wrapper-price">
                                <span>${product.price!""}<i class="fas fa-ruble-sign fa-sm"></i></span>
                                <#if product.discount??>
                                    <span class="section-product-table-card-wrapper-price_old">${(product.price * 100)/(100-product.discount)}<i class="fas fa-ruble-sign fa-sm"></i></span>
                                <#else>
                                </#if>
                            </div>
                        </div>
                        <div class="section-product-table-card-bottom">
                            <button type="submit" class="section-product-table-card-bottom-buy">
                                <i>Купить</i>
                            </button>
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