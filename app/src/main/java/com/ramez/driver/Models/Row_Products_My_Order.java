package com.ramez.driver.Models;


import java.io.Serializable;
import java.util.ArrayList;

public class Row_Products_My_Order implements Serializable {


    private  int ip_id;
    private  int pro_id;
    private String pro_qua;
    private String pro_name;
    private String pro_total;
    private String pro_image;
    private String pro_name_en;
    private String pro_barcode;

    private ArrayList<ExtraDM> extraDMS;
    private ArrayList<RequestProduct> requestProducts;
    private Boolean type;
    private int is_picked;
    private int InvId;

    public int getPro_id() {
        return pro_id;
    }

    public void setPro_id(int pro_id) {
        this.pro_id = pro_id;
    }

    public ArrayList<RequestProduct> getRequestProducts() {
        return requestProducts;
    }

    public void setRequestProducts(ArrayList<RequestProduct> requestProducts) {
        this.requestProducts = requestProducts;
    }

    public Row_Products_My_Order(int ip_id, int pro_id, String pro_qua, String pro_barcode, String pro_name, String pro_name_en, String pro_total,
                                 ArrayList<ExtraDM> extraDMS, String pro_image, boolean type, int is_picked, int InvId){
        this.ip_id=ip_id;
        this.pro_id=pro_id;
        this.pro_qua=pro_qua;
        this.pro_name=pro_name;
        this.extraDMS=extraDMS;
        this.pro_total=pro_total;
        this.pro_image=pro_image;
        this.pro_name_en=pro_name_en;
        this.pro_barcode=pro_barcode;
        this.type=type;
        this.is_picked=is_picked;
        this.InvId=InvId;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public String getPro_name_en() {
        return pro_name_en;
    }

    public void setPro_name_en(String pro_name_en) {
        this.pro_name_en = pro_name_en;
    }

    public int getIp_id() {
        return ip_id;
    }

    public String getPro_barcode() {
        return pro_barcode;
    }

    public void setPro_barcode(String pro_barcode) {
        this.pro_barcode = pro_barcode;
    }

    public int getInvId() {
        return InvId;
    }

    public void setInvId(int invId) {
        InvId = invId;
    }

    public void setIp_id(int ip_id) {
        this.ip_id = ip_id;
    }

    public String getPro_qua() {
        return pro_qua;
    }

    public void setPro_qua(String pro_qua) {
        this.pro_qua = pro_qua;
    }

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }

    public String getPro_total() {
        return pro_total;
    }

    public void setPro_total(String pro_total) {
        this.pro_total = pro_total;
    }

    public ArrayList<ExtraDM> getExtraDMS() {
        return extraDMS;
    }

    public String getPro_image() {
        return pro_image;
    }

    public void setPro_image(String pro_image) {
        this.pro_image = pro_image;
    }

    public void setExtraDMS(ArrayList<ExtraDM> extraDMS) {
        this.extraDMS = extraDMS;
    }


    public int getIs_picked() {
        return is_picked;
    }

    public void setIs_picked(int is_picked) {
        this.is_picked = is_picked;
    }
}
