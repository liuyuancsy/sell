package com.liuyuan.sell.controller;

import com.liuyuan.sell.dataobject.ProductCategory;
import com.liuyuan.sell.enums.ResultEnum;
import com.liuyuan.sell.exception.SellException;
import com.liuyuan.sell.form.CategoryForm;
import com.liuyuan.sell.repository.ProductCategoryRepository;
import com.liuyuan.sell.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Map;

/**
 * 类目操作类
 */
@Controller
@RequestMapping("/seller/category/")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private  ProductCategoryRepository productCategoryRepository;

    @GetMapping("list")
    public ModelAndView list(@RequestParam(value="page" ,defaultValue = "1")Integer page,
                             @RequestParam(value="size",defaultValue = "10")Integer size,
                             Pageable pageable,Map<String, Object> map) {
        PageRequest pageRequest = PageRequest.of(page-1, size);
        Page<ProductCategory> productCategoryPage;
        try {
              productCategoryPage = categoryService.findAll(pageRequest);
        }catch (SellException e){
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("/common/error", map);
        }
        map.put("categoryPages", productCategoryPage.getContent());
        map.put("productCategoryPage", productCategoryPage);
        map.put("page", page);
        map.put("size", size);
        return new ModelAndView("/category/list",map);
    }

    @RequestMapping("index")
    public ModelAndView index(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                              Map<String, Object> map,
                              ProductCategory productCategory) {
        if (!StringUtils.isEmpty(categoryId)) {//修改
            productCategory=categoryService.findOne(categoryId);
            map.put("categoryForm", productCategory);
        }
            return new ModelAndView("/category/index", map);
        }

    @RequestMapping("save")
    public ModelAndView save(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                             Map<String, Object> map, @Valid  CategoryForm categoryForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/category/list");
            return new ModelAndView("/common/error", map);
        }
        ProductCategory productCategory = new ProductCategory();
        if (!StringUtils.isEmpty(categoryId)) {
            productCategory = categoryService.findOne(categoryId);
        }
        BeanUtils.copyProperties(categoryForm, productCategory);
        productCategoryRepository.save(productCategory);
        map.put("msg", ResultEnum.CATEGORY_SUCCESS.getMsg());
        map.put("url", "/sell/seller/category/list");
        return new ModelAndView("/common/success", map);


    }
}
