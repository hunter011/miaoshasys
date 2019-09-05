package com.muke.service.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by   Intellif Idea 2019.09
 * Author:  Wang Yun
 * Date:    2019-09-01
 * Time:    18:19
 */
public class ItemModel {
    // 商品id
    private Integer id;
    // 商品名
    @NotBlank(message = "商品名称不能为空")
    private String title;
    // 商品价格
    @NotNull(message = "商品价格不能为空")
    @Min(value = 0,message = "商品价格大于0")
    private BigDecimal price;
    // 库存
    @NotNull(message = "库存不能不填")
    private Integer stock;
    //商品描述
    @NotBlank(message = "描述不能为空")
    private String descript;
    //销量
    private Integer sales;
    //商品图片地址
    @NotBlank(message = "图片不能为空")
    private String imgUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
