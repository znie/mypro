package com.znie.mypro.jsoup;

import java.io.UnsupportedEncodingException;  
import java.net.URL;  
import java.net.URLEncoder;  
import java.text.SimpleDateFormat;  
import java.util.List;  
import java.util.TimeZone;  
  
import org.apache.commons.logging.LogFactory;  
  
import com.gargoylesoftware.htmlunit.BrowserVersion;  
import com.gargoylesoftware.htmlunit.HttpMethod;  
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;  
import com.gargoylesoftware.htmlunit.WebClient;  
import com.gargoylesoftware.htmlunit.WebRequest;  
import com.gargoylesoftware.htmlunit.html.HtmlPage;  

public class PriceCheckUtil {
	private PriceCheckUtil() {  
        
    }  
      
    private static final PriceCheckUtil instance = new PriceCheckUtil();  
      
    public static PriceCheckUtil getInstance() {  
        return instance;  
    }  
      
      
    /** 
     * ��Ʒ����ת�� 
     * @param productName ��Ʒ���� 
     * @return 
     */  
    public String getGbk(String productName){  
        String retGbk = "";  
        try {  
            retGbk = new String(productName.getBytes("UTF-8"), "GBK");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        return retGbk;  
    }  
    /** 
     * ���Ա���������ֽ���ת�� 
     * @param productName ��Ʒ���� 
     * @return 
     */  
    public String getUrlCode(String productName){  
        String retUrlCode = "";  
        try {  
            retUrlCode = URLEncoder.encode(productName, "utf8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        return retUrlCode;  
    }  
      
    /** 
     * ���б�list���ҵ���productName���ƶ���ߵ�ProductInfo 
     * 
     * @param productName 
     * @param list 
     * @return ���ƶ���ߵ�productName 
     */  
    public ProductInfo getSimilarity(String productName, List<ProductInfo> list) {  
        ProductInfo productInfo = null;  
        /** 
         * �ҵ�list�����е�productName���ַ���productName�����ƶȣ�������lens������ 
         */  
        double lens[] = new double[list.size()];  
        for (int i = 0; i < list.size() - 1; i++) {  
            lens[i] = sim(productName, list.get(i).getProductName());  
        }  
        /** 
         * �������������ƶ�maxLen 
         */  
        double maxLen = 0.0;  
        for (int i = 0; i < lens.length; i++) {  
            if (maxLen < lens[i]) {  
                maxLen = lens[i];  
            }  
        }  
        /** 
         * �������������ƶȵ�����maxLenIndex 
         */  
        int maxLenIndex = 0;  
        for (int i = 0; i < lens.length; i++) {  
            if (maxLen == lens[i]) {  
                maxLenIndex = i;  
            }  
        }  
        productInfo = list.get(maxLenIndex);  
        return productInfo;  
    }  
      
    /** 
     * ������������С��һ�� 
     * @param one 
     * @param two 
     * @param three 
     * @return 
     */  
    public int min(int one, int two, int three) {  
        int min = one;  
        if(two < min) {  
            min = two;  
        }  
        if(three < min) {  
            min = three;  
        }  
        return min;  
    }  
  
    /** 
     * ����ʸ������ 
     * Levenshtein Distance(LD) 
     * @param str1 
     * @param str2 
     * @return 
     */  
    public int ld(String str1, String str2) {  
        int d[][];    //����  
        int n = str1.length();  
        int m = str2.length();  
        int i;    //����str1��  
        int j;    //����str2��  
        char ch1;    //str1��  
        char ch2;    //str2��  
        int temp;    //��¼��ͬ�ַ�,��ĳ������λ��ֵ������,����0����1  
        if(n == 0) {  
            return m;  
        }  
        if(m == 0) {  
            return n;  
        }  
        d = new int[n+1][m+1];  
        for(i=0; i<=n; i++) {    //��ʼ����һ��  
            d[i][0] = i;  
        }  
        for(j=0; j<=m; j++) {    //��ʼ����һ��  
            d[0][j] = j;  
        }  
        for(i=1; i<=n; i++) {    //����str1  
            ch1 = str1.charAt(i-1);  
            //ȥƥ��str2  
            for(j=1; j<=m; j++) {  
                ch2 = str2.charAt(j-1);  
                if(ch1 == ch2) {  
                    temp = 0;  
                } else {  
                    temp = 1;  
                }  
                //���+1,�ϱ�+1, ���Ͻ�+tempȡ��С  
                d[i][j] = min(d[i-1][j]+1, d[i][j-1]+1, d[i-1][j-1]+temp);  
            }  
        }  
        return d[n][m];  
    }  
  
    /** 
     * �������ƶ� 
     * @param str1 
     * @param str2 
     * @return 
     */  
    public double sim(String str1, String str2) {  
        int ld = ld(str1, str2);  
        return 1 - (double) ld / Math.max(str1.length(), str2.length());  
    }  
      
    /**  
     * ����ת����hhmmss  
     * @param ms ����  
     * @return hh:mm:ss  
     */    
    public String msToss(long ms) {  
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");    
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));    
        String ss = formatter.format(ms);    
        return ss;    
    }  
      
    /** 
     * ��ֹhtmlunit��־��� 
     */  
    public void offLog(){  
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log",  
                "org.apache.commons.logging.impl.NoOpLog");  
    }  
    /** 
     * ��ȡ�Ա����� 
     * @param url 
     * @return 
     * @throws Exception 
     */  
    public String getXmlByHtmlunit(String url) throws Exception {  
        offLog();  
        String ret = "";  
        WebClient webClient = new WebClient(BrowserVersion.CHROME);  
        // 1 ����JS  
        webClient.getOptions().setJavaScriptEnabled(true);  
        // 2 ����Css���ɱ����Զ���������CSS������Ⱦ  
        webClient.getOptions().setCssEnabled(false);  
        // 3 �����ͻ����ض���  
        webClient.getOptions().setRedirectEnabled(true);  
        // 4 JS���д���ʱ���Ƿ��׳��쳣  
        webClient.getOptions().setThrowExceptionOnScriptError(false);  
        // 5AJAX support  
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());  
        // 6 ���ó�ʱ  
        webClient.getOptions().setTimeout(Constants.TIMEOUT);  
        WebRequest webRequest = new WebRequest(new URL(url));  
        webRequest.setHttpMethod(HttpMethod.GET);  
        HtmlPage page = webClient.getPage(webRequest);  
        webClient.waitForBackgroundJavaScript(10000);  
        ret = page.asXml();  
        webClient.closeAllWindows();  
        return ret;  
    }  
}
