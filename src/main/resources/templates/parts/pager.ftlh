<#macro pager url page>
    <#if page.getTotalPages() gt 7>
        <#if page.getNumber() + 1 gt 6 && page.getNumber() + 1 lt page.getTotalPages()-3>
            <#assign
                pageNumber = page.getNumber() + 1
                pageBefore = pageNumber-4..pageNumber-1
                pageAfter = pageNumber+1..pageNumber+3
                body = pageNumber-4..pageNumber+3
            >
        <#elseif  page.getNumber() + 1 lt 7>
            <#assign body = 1..7>
        <#else>
            <#assign body = (page.getTotalPages() - 7)..page.getTotalPages()>
        </#if>
    <#else>
        <#assign body = 1..page.getTotalPages()>
    </#if>
    <section class="section-outer section-page">
        <div class="section-inner">
            <ul class="section-page-wrapper">
                <li  class="section-page-wrapper-item"><a href="/${url}?page=0" class="section-page-wrapper-item-link"><p><i class="far fa-angle-double-left"></i></p></a></li>
                <li  class="section-page-wrapper-item"><a href="/${url}?page=${page.getNumber()-1}" class="section-page-wrapper-item-link"><p><i class="far fa-angle-left"></i></p></a></li>
                <#list body as p>
                    <#if (p-1) == page.getNumber()>
                        <li  class="section-page-wrapper-item active"><a href="/${url}?page=${p-1}" class="section-page-wrapper-item-link"><p>${p}</p></a></li>
                    <#else>
                        <li  class="section-page-wrapper-item"><a href="/${url}?page=${p-1}" class="section-page-wrapper-item-link"><p>${p}</p></a></li>
                    </#if>
                </#list>
                <li  class="section-page-wrapper-item"><a href="/${url}?page=${page.getNumber()+1}" class="section-page-wrapper-item-link"><p><i class="far fa-angle-right"></i></p></a></li>
                <li  class="section-page-wrapper-item"><a href="/${url}?page=${page.getTotalPages()-1}" class="section-page-wrapper-item-link"><p><i class="far fa-angle-double-right"></i></p></a></li>
            </ul>
        </div>
    </section>
</#macro>