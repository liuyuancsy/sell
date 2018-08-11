<html>
<head>
    <meta charset="utf-8">
    <title>卖家商品列表</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <table class="table table-bordered table-condensed">
                <thead>
                <tr>
                    <th>订单id</th>
                    <th>姓名</th>
                    <th>手机号</th>
                </tr>
                </thead>
                <tbody>
                <td>${orderDTO.orderId}</td>
                <td>${orderDTO.buyerName}</td>
                <td>${orderDTO.buyerPhone}</td>

                </tbody>
            </table>

            <table class="table table-bordered table-condensed">
                <thead>
                <tr>
                    <th>商品名称</th>
                    <th>商品数量</th>
                    <th>商品总额</th>
                </tr>
                </thead>
                <tbody>

                        <#list orderDTO.orderDetailList as orderDetail>
                        <tr>
                            <td>${orderDetail.productName}</td>
                            <td>${orderDetail.productQuantity}</td>
                            <td>${orderDetail.productPrice*orderDetail.productQuantity}元</td>
                        </tr>
                        </#list>
                </tbody>
            </table>
        <#--操作-->
            <div class="col-md-12 column">
                <#if orderDTO.getOrderStatusEnum().msg == "新订单">
                    <a href="/sell/seller/order/finish?orderId=${orderDTO.orderId}" type="button" class="btn btn-default btn-primary">完结订单</a>
                    <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}" type="button" class="btn btn-default btn-danger">取消订单</a>
                </#if>
            </div>


        </div>
    </div>
</div>

</body>
</html>