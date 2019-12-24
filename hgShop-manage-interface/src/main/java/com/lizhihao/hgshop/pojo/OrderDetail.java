package com.lizhihao.hgshop.pojo;

import java.io.Serializable;

/**
 * @author LZH
 * @date 2019/12/23
 * Describe: 订单列表实体类
 */
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1630574276743346025L;

    private Integer id;
    private String orderId; //订单号
    private String skuId;    //商品id
    private Integer num; //数量
    private String title; //标题
    private Integer price; //价格
    private String image; //图片

    public OrderDetail() {
        super();
        // TODO Auto-generated constructor stub
    }

    public OrderDetail(Integer id, String orderId, String skuId, Integer num, String title, Integer price, String image) {
        super();
        this.id = id;
        this.orderId = orderId;
        this.skuId = skuId;
        this.num = num;
        this.title = title;
        this.price = price;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "OrderDetail [id=" + id + ", orderId=" + orderId + ", skuId=" + skuId + ", num=" + num + ", title="
                + title + ", price=" + price + ", image=" + image + "]";
    }

}
