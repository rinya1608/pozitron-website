<#assign
    known = Session.SPRING_SECURITY_CONTEXT??
>

<#if known>
    <#assign
        user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
        username = user.getUsername()
        name = user.getName()
        surname = (user.getSurname()!)
        isAdmin = user.isAdmin()
    >
<#else>
    <#assign
        username = " "
        name = " "
        surname = " "
        isAdmin = false
    >
</#if>