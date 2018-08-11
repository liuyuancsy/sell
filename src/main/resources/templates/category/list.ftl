<html>
<#include "../common/header.ftl">

<body>
<div id="wrapper" class="toggled">

<#--边栏sidebar-->
    <#include "../common/nav.ftl">

<#--主要内容content-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>类目id</th>
                            <th>名字</th>
                            <th>type</th>
                            <th>创建时间</th>
                            <th>修改时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>

                        <#list categoryPages as category>
                        <tr>
                            <td>${category.categoryId}</td>
                            <td>${category.categoryName}</td>
                            <td>${category.categoryType}</td>
                            <td>${category.createTime}</td>
                            <td>${category.updateTime}</td>
                            <td><a href="/sell/seller/category/index?categoryId=${category.categoryId}">修改</a></td>
                        </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-12 column">
        <ul class="pagination pull-right=">
            <li>
                    <#if page lte 1>
                        <a class="disabled">上一页</a>
                    <#else>
                        <a href="/sell/seller/category/list?page=${page-1}&size=${size}">上一页</a>
                    </#if>
            </li>
                <#list 1..productCategoryPage.totalPages as index>
                <li>
                    <#if page==index>
                        <a class="disabled">${index}</a>
                    <#else>
                    <a href="/sell/seller/category/list?page=${index}&size=${size}">${index}</a>
                    </#if>
                </li>
                </#list>
            <li>
                    <#if page gte productCategoryPage.totalPages><!--如果页数大于等于总页数-->
                <a class="disabled">下一页</a>
            <#else>
                    <a href="/seller/category/list?page=${page+1}&size=${size}">下一页</a>
            </#if>
            </li>
        </ul>
    </div>

</div>
</body>
</html>