package com.ramez.driver.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OrderItemDetail {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("order_id")
    @Expose
    private int orderId;
    @SerializedName("product_id")
    @Expose
    private int productId;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("h_product_name")
    @Expose
    private String hProductName;
    @SerializedName("h_unit_name")
    @Expose
    private String hUnitName;
    @SerializedName("product_barcode_id")
    @Expose
    private int productBarcodeId;
    @SerializedName("barcode")
    @Expose
    private String barcode;
    @SerializedName("product_price")
    @Expose
    private double productPrice;
    @SerializedName("cart_price")
    @Expose
    private double cartPrice;
    @SerializedName("quantity")
    @Expose
    private int quantity;
    @SerializedName("stock_qty")
    @Expose
    private int stockQty;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("limit_qty")
    @Expose
    private int limitQty;
    @SerializedName("from_offer")
    @Expose
    private String fromOffer;
    @SerializedName("end_offer")
    @Expose
    private String endOffer;
    @SerializedName("from_date")
    @Expose
    private String fromDate;
    @SerializedName("brand_name")
    @Expose
    private String brandName;
    @SerializedName("remark")
    @Expose
    private String remark;

    private Boolean type;
    private int is_picked;

    private ArrayList<ExtraDM> extraDMS;
    private java.util.ArrayList<RequestProduct> requestProducts;


    public OrderItemDetail(int id, int orderId, int productId, String productName, String hProductName, String hUnitName, int productBarcodeId, String barcode, double productPrice, double cartPrice, int quantity, int stockQty, String image, int limitQty, String fromOffer, String endOffer, String fromDate, String brandName, String remark, Boolean type, int is_picked, ArrayList<ExtraDM> extraDMS) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.hProductName = hProductName;
        this.hUnitName = hUnitName;
        this.productBarcodeId = productBarcodeId;
        this.barcode = barcode;
        this.productPrice = productPrice;
        this.cartPrice = cartPrice;
        this.quantity = quantity;
        this.stockQty = stockQty;
        this.image = image;
        this.limitQty = limitQty;
        this.fromOffer = fromOffer;
        this.endOffer = endOffer;
        this.fromDate = fromDate;
        this.brandName = brandName;
        this.remark = remark;
        this.type = type;
        this.is_picked = is_picked;
        this.extraDMS = extraDMS;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String gethProductName() {
        return hProductName;
    }

    public void sethProductName(String hProductName) {
        this.hProductName = hProductName;
    }

    public String gethUnitName() {
        return hUnitName;
    }

    public void sethUnitName(String hUnitName) {
        this.hUnitName = hUnitName;
    }

    public int getProductBarcodeId() {
        return productBarcodeId;
    }

    public void setProductBarcodeId(int productBarcodeId) {
        this.productBarcodeId = productBarcodeId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public double getCartPrice() {
        return cartPrice;
    }

    public void setCartPrice(double cartPrice) {
        this.cartPrice = cartPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStockQty() {
        return stockQty;
    }

    public void setStockQty(int stockQty) {
        this.stockQty = stockQty;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getLimitQty() {
        return limitQty;
    }

    public void setLimitQty(int limitQty) {
        this.limitQty = limitQty;
    }

    public String getFromOffer() {
        return fromOffer;
    }

    public void setFromOffer(String fromOffer) {
        this.fromOffer = fromOffer;
    }

    public String getEndOffer() {
        return endOffer;
    }

    public void setEndOffer(String endOffer) {
        this.endOffer = endOffer;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public int getIs_picked() {
        return is_picked;
    }

    public void setIs_picked(int is_picked) {
        this.is_picked = is_picked;
    }

    public ArrayList<ExtraDM> getExtraDMS() {
        return extraDMS;
    }

    public void setExtraDMS(ArrayList<ExtraDM> extraDMS) {
        this.extraDMS = extraDMS;
    }

    public ArrayList<RequestProduct> getRequestProducts() {
        return requestProducts;
    }

    public void setRequestProducts(ArrayList<RequestProduct> requestProducts) {
        this.requestProducts = requestProducts;
    }
}
