package com.znie.wechat.service;

import java.util.Date;  
import java.util.Map;  
  
import javax.servlet.http.HttpServletRequest;  
  
import org.apache.log4j.Logger;  
import org.springframework.stereotype.Service;

import com.znie.wechat.model.resp.TextMessage;
import com.znie.wechat.util.Constant;
import com.znie.wechat.util.MessageUtil;  
  
@Service("coreService")  
public class CoreServiceImpl implements CoreService{  
  
    public static Logger log = Logger.getLogger(CoreServiceImpl.class);  
      
      
    @Override  
    public String processRequest(HttpServletRequest request) {  
        String respMessage = null;  
        try {  
            // xml请求解析  
            Map<String, String> requestMap = MessageUtil.parseXml(request);  
  
            // 发送方帐号（open_id）  
            String fromUserName = requestMap.get("FromUserName");  
            // 公众帐号  
            String toUserName = requestMap.get("ToUserName");  
            // 消息类型  
            String msgType = requestMap.get("MsgType");  
  
            TextMessage textMessage = new TextMessage();  
            textMessage.setToUserName(fromUserName);  
            textMessage.setFromUserName(toUserName);  
            textMessage.setCreateTime(new Date().getTime());  
            textMessage.setMsgType(Constant.RESP_MESSAGE_TYPE_TEXT);  
            textMessage.setFuncFlag(0);  
            // 文本消息  
            if (msgType.equals(Constant.REQ_MESSAGE_TYPE_TEXT)) {  
                // 接收用户发送的文本消息内容  
                String content = requestMap.get("Content");  
  
                if ("1".equals(content)) {  
                    textMessage.setContent("1是很好的");  
                    // 将文本消息对象转换成xml字符串  
                    respMessage = MessageUtil.textMessageToXml(textMessage);  
                }else if ("2".equals(content)) {  
                    textMessage.setContent("我不是2货");  
                    // 将文本消息对象转换成xml字符串  
                    respMessage = MessageUtil.textMessageToXml(textMessage);  
                }  
            }   
              
              
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return respMessage;  
    }  
  
  
}  