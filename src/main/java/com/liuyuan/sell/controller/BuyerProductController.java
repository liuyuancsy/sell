//package com.liuyuan.sell.controller;
//
//import com.liuyuan.sell.utils.ResultVoUtil;
//import com.liuyuan.sell.VO.ProductInfoVO;
//import com.liuyuan.sell.VO.ProductVO;
//import com.liuyuan.sell.VO.ResultVO;
//import com.liuyuan.sell.dataobject.ProductCategory;
//import com.liuyuan.sell.dataobject.ProductInfo;
//import com.liuyuan.sell.service.impl.CatagoryServiceImpl;
//import com.liuyuan.sell.service.impl.ProductInfoServiceImpl;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 买家商品
// */
//@RestController
//@RequestMapping("/buyer/product/")
//public class BuyerProductController {
//    @Autowired
//    private ProductInfoServiceImpl productInfoService;
//    @Autowired
//    private CatagoryServiceImpl catagoryService;
//    Logger logger = LoggerFactory.getLogger(BuyerProductController.class);
//
//    @GetMapping(value = "list")
//    public ResultVO list(){
//        //先查询出所有的已上架商品
//        List<ProductInfo> productInfoList =productInfoService.findUpAll();
//        logger.info("一次性查出所有的商品类目");
//        List<ProductCategory> productCategoryList =null;
//        List<Integer> cateGoryTypList = new ArrayList<Integer>();
//        for(ProductInfo productInfo : productInfoList){
//            logger.info("类目{}"+productInfo.getCategoryType());
//            cateGoryTypList.add(productInfo.getCategoryType());
//        }
//        productCategoryList = catagoryService.findByCategoryIn(cateGoryTypList);
//        /*类目list*/
//        List<ProductVO> productVOList = new ArrayList<ProductVO>();
//            for(ProductCategory productCategory : productCategoryList){
//                logger.info("开始遍历商品类目信息");
//                ProductVO productVO = new ProductVO();
//                logger.info("将类目信息赋值给ProductVO对象");
//                productVO.setCategoryType(productCategory.getCategoryType());
//                productVO.setCategoryName(productCategory.getCategoryName());
//                /*商品list*/
//                List<ProductInfoVO> productInfoVOList = new ArrayList<ProductInfoVO>();//栽的坑不浅啊！！！
//                for(ProductInfo ProductInfo:productInfoList){
//                    logger.info("开始遍历类目下的商品信息，第二层for循环");
//                    if(ProductInfo.getCategoryType().equals(productCategory.getCategoryType())){
//                        logger.info("将该类目下的商品信息添加进list");
//                        ProductInfoVO productInfoVO = new ProductInfoVO();
//                        BeanUtils.copyProperties(ProductInfo,productInfoVO);
//                        productInfoVOList.add(productInfoVO);//这一步关键,第一次写错
//                    }
//                }
//                logger.info("==productInfoVOList==="+productInfoVOList);
//                productVO.setProductInfoVOList(productInfoVOList);//这一步调了我两个小时，最终还是看视频的，呵呵！！！
//                productVOList.add(productVO);//这一步要搞懂！
//            }
//
//        return ResultVoUtil.success(productVOList);
//    }
//}
