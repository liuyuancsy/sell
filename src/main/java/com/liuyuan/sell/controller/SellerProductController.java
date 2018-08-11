package com.liuyuan.sell.controller;

import com.liuyuan.sell.dataobject.ProductCategory;
import com.liuyuan.sell.dataobject.ProductInfo;
import com.liuyuan.sell.enums.ResultEnum;
import com.liuyuan.sell.exception.SellException;
import com.liuyuan.sell.form.ProductForm;
import com.liuyuan.sell.service.CategoryService;
import com.liuyuan.sell.service.ProductInfoService;
import com.liuyuan.sell.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/seller/product")
public class SellerProductController {
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map){
        PageRequest request = PageRequest.of(page - 1, size);
        Page<ProductInfo> productInfoPage = productInfoService.findAll(request);
        map.put("productInfoPage", productInfoPage);
        map.put("currentPage", page);
        map.put("size", size);
        List<ProductCategory> productCategoryList =categoryService.findAll();
        map.put("productCategoryList",productCategoryList);
        return new ModelAndView("product/list", map);

    }

    //商品上下架
    @RequestMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("productId") String productId, Map<String, Object> map) {
        try {
            productInfoService.onSale(productId);
        } catch (SellException s) {
            map.put("msg", s.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }


    @RequestMapping("off_sale")
    public ModelAndView offSale(@RequestParam("productId") String productId, Map<String, Object> map){
        try {
            productInfoService.offSale(productId);
        } catch (SellException s) {
            map.put("msg", s.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }


    //表单
    @RequestMapping("index")
    public ModelAndView index(@RequestParam(value="productId", required=false) String productId, Map<String, Object> map,ProductInfo productInfo) {
        if(!StringUtils.isEmpty(productId)) {
        try {
            productInfo= productInfoService.findOne(productId);
        } catch (SellException s) {
            map.put("url", "/sell/seller/product/list");
            map.put("msg", s.getMessage());
        }
            map.put("productInfo", productInfo);
        }
        List<ProductCategory> productCategoryList = categoryService.findAll();
        map.put("productCategoryList",productCategoryList);

        return new ModelAndView("/product/index", map);

    }

    //修改/保存商品
    @PostMapping("save")
    public ModelAndView save(@RequestParam(value = "productId", required = false) String productId,
                             @Valid ProductForm productForm,
                             BindingResult bindingResult,
                             Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/sell/seller/product/index");
            return new ModelAndView("/common/error", map);
        }
        ProductInfo productInfo =new ProductInfo();
        if (!StringUtils.isEmpty(productForm.getProductId())) {
            productInfo = productInfoService.findOne(productId);
        }else{
            productForm.setProductId(KeyUtil.keyGenerate());//新增
        }
        try {
            BeanUtils.copyProperties(productForm, productInfo);
            productInfoService.save(productInfo);
        } catch (SellException s) {
            map.put("msg", ResultEnum.PRODUCT_UPDATE_FAIL);
            map.put("url", "/sell/seller/product/index");
            return new ModelAndView("/common/error", map);
        }
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("/common/success", map);

    }
}
