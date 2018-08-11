
<html>
<#include "../common/header.ftl">
<body>
 <div id="wrapper" class="toggled">

<#--边栏sidebar-->
<#include "../common/nav.ftl">
    <div id="page-content-wrapper">
        <div class="container">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <form role="form" method="post" action="/sell/seller/product/save">
                        <div class="form-group">
                            <label for="text">商品名称</label>
                            <input class="form-control" value="${productInfo.productName!''}" name="productName" maxlength="20" type="text" />
                        </div>

                        <div class="form-group">
                            <label for="text">商品类目</label>
                            <select class="form-control" name="categoryType" type="select">
                                <#list productCategoryList as categoryList>
                                    <#if productInfo.categoryType?? &&categoryList.categoryType==productInfo.categoryType>
                                      <option selected value="${productInfo.categoryType!''}">${categoryList.categoryName}</option>
                                    <#else>
                                      <option value="${categoryList.categoryType}">${categoryList.categoryName}</option>
                                    </#if>
                                </#list>
                            </select>
                        </div>

                        <div class="form-group">
                            <label>价格</label>
                            <input name="productPrice" type="text" class="form-control" value="${productInfo.productPrice!''}"/>
                        </div>
                        <div class="form-group">
                            <label>库存</label>
                            <input name="productStock" type="number" class="form-control" value="${(productInfo.productStock)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>描述</label>
                            <input name="productDescription" type="text" class="form-control" value="${(productInfo.productDescription)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>图片</label>
                            <img height="100" width="100" src="${(productInfo.productIcon)!''}" alt="">
                            <input name="productIcon" type="text" class="form-control" value="${(productInfo.productIcon)!''}"/>
                        </div>

                        <input hidden type="text" name="productId" value="${productInfo.productId!''}">
                       <#-- <input hidden type="text" name="createTime" value="${(productInfo.createTime)!''}">-->
                         <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>