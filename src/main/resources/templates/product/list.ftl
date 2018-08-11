<html>
<#include "../common/header.ftl">
<body>
<div id="wrapper" class="toggled">

<#--边栏sidebar-->
<#include "../common/nav.ftl">


<#--主要内容content-->
    <div id="page-content-wrapper">

        <div class="container">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>商品id</th>
                            <th>名称</th>
                            <th>图片</th>
                            <th>单价</th>
                            <th>库存</th>
                            <th>描述</th>
                            <th>类目</th>
                            <th>创建时间</th>
                            <th>修改时间</th>
                            <th colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>

                        <#list productInfoPage.content as productInfo>
                        <tr>
                            <td>${productInfo.productId}</td>
                            <td>${productInfo.productName}</td>
                            <td><img height="100" width="100" src="${productInfo.productIcon!''}" alt=""></td>
                            <td>${productInfo.productPrice?string.number}</td>
                            <td>${productInfo.productStock}</td>
                            <td>${productInfo.productDescription}</td>
                             <#list productCategoryList as categoryList>
                             <#if categoryList.categoryType==productInfo.categoryType>
                            <td>${categoryList.categoryName}</td>
                             </#if>
                             </#list>
                            <td>${productInfo.createTime}</td>
                            <td>${productInfo.updateTime}</td>
                            <td><a href="/sell/seller/product/index?productId=${productInfo.productId}">修改</a></td>
                            <td>
                                <#if productInfo.getProductStatusEnum().message=="上架">
                                    <a href="/sell/seller/product/off_sale?productId=${productInfo.productId}">下架</a>
                                <#else>
                                    <a href="/sell/seller/product/on_sale?productId=${productInfo.productId}">上架</a>
                                </#if>

                            </td>
                        </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
                <div class="col-md-12 column">
                    <ul class="pagination pull-right=">
                        <li>
                    <#if currentPage lte 1>
                        <a class="disabled">上一页</a>
                    <#else>
                        <a href="/sell/seller/product/list?page=${currentPage-1}&size=${size}">上一页</a>
                    </#if>
                        </li>
                <#list 1..productInfoPage.totalPages as index>
                <li>
                    <#if currentPage==index>
                        <a class="disabled">${index}</a>
                    <#else>
                    <a href="/sell/seller/product/list?page=${index}&size=${size}">${index}</a>
                    </#if>
                </li>
                </#list>
                        <li>
                    <#if currentPage gte productInfoPage.totalPages><!--如果页数大于等于总页数-->
                            <a class="disabled">下一页</a>
                        <#else>
                    <a href="/seller/product/list?page=${currentPage+1}&size=${size}">下一页</a>
                        </#if>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>