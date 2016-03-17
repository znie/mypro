package com.znie.mypro.jsoup.jd;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.znie.mypro.jsoup.Constants;
import com.znie.mypro.jsoup.PriceCheckUtil;
import com.znie.mypro.jsoup.ProductInfo;
import com.znie.mypro.jsoup.ProductList;

public class JDProductList implements ProductList{
	private String jdUrl;  
    
    private String productName;  
      
    private static PriceCheckUtil pcu = PriceCheckUtil.getInstance();  
      
    public JDProductList(String jdUrl, String productName){  
        this.jdUrl = jdUrl;  
        this.productName = productName;  
    }  
  
    @Override  
    public List<ProductInfo> getProductList() {  
        List<ProductInfo> jdProductList = new ArrayList<ProductInfo>();  
        ProductInfo productInfo = null;  
        String url = "";  
        for(int i = 0; i < 10; i++){  
            try {  
                System.out.println("JD Product ��[" + (i + 1) + "]ҳ");  
                if(i == 0) {  
                    url = jdUrl;  
                }else{  
                    url = Constants.JDURL + pcu.getGbk(productName) + Constants.JDENC + Constants.JDPAGE + (i + 1);  
                }  
                System.out.println(url);  
                Document document = Jsoup.connect(url).timeout(5000).get();  
                Elements uls = document.select("ul[class=gl-warp clearfix]");  
                Iterator<Element> ulIter = uls.iterator();  
                while(ulIter.hasNext()) {  
                    Element ul = ulIter.next();  
                    Elements lis = ul.select("li[data-sku]");  
                    Iterator<Element> liIter = lis.iterator();  
                    while(liIter.hasNext()) {  
                        Element li = liIter.next();  
                        Element div = li.select("div[class=gl-i-wrap]").first();  
                        Elements title = div.select("div[class=p-name p-name-type-2]>a");  
                        String productName = title.attr("title"); //�õ���Ʒ����  
                        Elements price = div.select(".p-price>strong");  
                        String productPrice =price.attr("data-price"); //�õ���Ʒ�۸�  
                        productInfo = new ProductInfo();  
                        productInfo.setProductName(productName);  
                        productInfo.setProductPrice(productPrice);  
                        jdProductList.add(productInfo);  
                    }  
                }  
            } catch(Exception e) {  
                System.out.println("Get JD product has error [" + url + "]");  
                System.out.println(e.getMessage());  
            }  
        }  
        return jdProductList;  
    }  
      
    public static void main(String[] args) {  
        try {  
            String productName = "���";  
            String jdUrl = Constants.JDURL + pcu.getGbk(productName)  + Constants.JDENC;  
            List<ProductInfo> list = new JDProductList(jdUrl, productName).getProductList();  
            System.out.println(list.size());  
            for(ProductInfo pi : list){  
                System.out.println(pi.getProductName() + "  " + pi.getProductPrice());  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
}  
