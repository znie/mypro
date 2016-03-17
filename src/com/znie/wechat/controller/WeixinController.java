package com.znie.wechat.controller;

import java.io.IOException;  
import java.io.PrintWriter;  
import java.io.UnsupportedEncodingException;  
  
import javax.annotation.Resource;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
  
import org.springframework.stereotype.Controller;  
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RequestMethod;

import com.znie.wechat.service.CoreService;
import com.znie.wechat.util.SignUtil;  
  
  
@Controller  
@RequestMapping("/znie/wechat")  
public class WeixinController {  
  
    @Resource(name="coreService")  
    private CoreService coreService;  
      
    @RequestMapping(method = RequestMethod.GET)  
    public void get(HttpServletRequest request, HttpServletResponse response) {  
    	System.out.println("====j进来了");
        // 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。  
        String signature = request.getParameter("signature");  
        // 时间戳  
        String timestamp = request.getParameter("timestamp");  
        // 随机数  
        String nonce = request.getParameter("nonce");  
        // 随机字符串  
        String echostr = request.getParameter("echostr");  
  
        PrintWriter out = null;  
        try {  
            out = response.getWriter();  
            // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，否则接入失败  
            if (SignUtil.checkSignature(signature, timestamp, nonce)) {  
                out.print(echostr);  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            out.close();  
            out = null;  
        }  
    }  
  
    @RequestMapping(method = RequestMethod.POST)  
    public void post(HttpServletRequest request, HttpServletResponse response) {  
    	try {  
            request.setCharacterEncoding("UTF-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        response.setCharacterEncoding("UTF-8");  
  
        // 调用核心业务类接收消息、处理消息  
        String respMessage = coreService.processRequest(request);  
  
        // 响应消息  
        PrintWriter out = null;  
        try {  
            out = response.getWriter();  
            out.print(respMessage);  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            out.close();  
            out = null;  
        }  
    }  
  
}  