package com.liuyuan.sell.dataobject.mapper;

        import com.liuyuan.sell.dataobject.ProductCategory;
        import org.apache.ibatis.annotations.Insert;

public interface ProductCatagoryMapper {
    @Insert("insert into product_category(category_name,category_type)values(#{categoryName,jdbcType=VARCHAR},#{categoryType,jdbcType=INTEGER})")
    int insertByObject(ProductCategory productCategory);


}
