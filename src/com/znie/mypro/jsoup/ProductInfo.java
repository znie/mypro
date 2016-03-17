package com.znie.mypro.jsoup;

import java.io.Serializable;
import java.util.Date;

public class ProductInfo implements Serializable{  
  
    private static final long serialVersionUID = 8179244535272774089L;  
      
    /** 
     * ��ƷID 
     */  
    private String productid;  
    /** 
     * ��Ʒ���� 
     */  
    private String productName;  
    /** 
     * ��Ʒ�۸� 
     */  
    private String productPrice;  
    /** 
     * �����۱��� 
     */  
    private String tradeNum;  
    /** 
     * ��ƷURL 
     */  
    private String productUrl;  
    /** 
     * ��Ʒ�������� 
     */  
    private String shopName;  
    /** 
     * �������� 
     */  
    private String ecName;  
    /** 
     * ��ȡ������� 
     */  
    private Date date;  
      
    public String getProductid() {  
        return productid;  
    }  
    public void setProductid(String productid) {  
        this.productid = productid;  
    }  
    public String getProductName() {  
        return productName;  
    }  
    public void setProductName(String productName) {  
        this.productName = productName;  
    }  
    public String getProductPrice() {  
        return productPrice;  
    }  
    public void setProductPrice(String productPrice) {  
        this.productPrice = productPrice;  
    }  
    public String getTradeNum() {  
        return tradeNum;  
    }  
    public void setTradeNum(String tradeNum) {  
        this.tradeNum = tradeNum;  
    }  
    public String getProductUrl() {  
        return productUrl;  
    }  
    public void setProductUrl(String productUrl) {  
        this.productUrl = productUrl;  
    }  
    public String getShopName() {  
        return shopName;  
    }  
    public void setShopName(String shopName) {  
        this.shopName = shopName;  
    }  
    public String getEcName() {  
        return ecName;  
    }  
    public void setEcName(String ecName) {  
        this.ecName = ecName;  
    }  
    public Date getDate() {  
        return date;  
    }  
    public void setDate(Date date) {  
        this.date = date;  
    }  
      
}
