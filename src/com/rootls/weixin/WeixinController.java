package com.rootls.weixin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 14-4-9
 * Time: 下午2:58
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/weixin")
public class WeixinController {

    public static String token = "weiwei745054025";

    @ResponseBody
    @RequestMapping("/checkJoin")
    public String checkJoin(HttpServletRequest request, HttpServletResponse response,
            String signature,String timestamp, String nonce,String echostr){
        if(echostr!=null){//为验证请求
            return echostr;
        }
        return null;

    }

//    @ResponseBody @RequestMapping("checkJoin")
//    public String checkJoin(HttpServletRequest request, HttpServletResponse response,
//                            String signature,String timestamp, String nonce,String echostr) throws IOException {
//        String postStr=null;
//        try{
//            postStr=this.readStreamParameter(request.getInputStream());
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//
//        if (null!=postStr&&!postStr.isEmpty()){
//            Document document=null;
//            try{
//                document = DocumentHelper.parseText(postStr);
//            }catch(Exception e){
//                e.printStackTrace();
//            }
//            if(null==document){
//                return"";
//            }
//            Element root=document.getRootElement();
//            String fromUsername = root.elementText("FromUserName");
//            String toUsername = root.elementText("ToUserName");
//            String keyword = root.elementTextTrim("Content");
//            String msgId = root.elementTextTrim("MsgId");
//            String time = new Date().getTime()+"";
//            String textTpl = "<xml>"+
//                    "<ToUserName><![CDATA[%1$s]]></ToUserName>"+
//                    "<FromUserName><![CDATA[%2$s]]></FromUserName>"+
//                    "<CreateTime>%3$s</CreateTime>"+
//                    "<MsgType><![CDATA[%4$s]]></MsgType>"+
//                    "<Content><![CDATA[%5$s]]></Content>"+
//                    "<FuncFlag>0</FuncFlag>"+
//                    "</xml>";
//
//            if(null!=keyword&&!keyword.equals(""))
//            {
//                String msgType = "text";
//                String contentStr =keyword+ " 你好";
//                String resultStr = textTpl.format(textTpl, fromUsername, toUsername, time, msgType, contentStr);
//
//                Weixin weixin = new Weixin();
//
//                weixin.setFromUserName(fromUsername);
//                weixin.setCreateTime(time);
//                weixin.setContent(contentStr);
//                weixin.setMsgType(msgType);
//                weixin.setToUserName(toUsername);
//                weixin.setMsgId(msgId);
//
//                return resultStr;
//            }else{
//                return "Input something...";
//            }
//
//        }else {
//            return "";
//        }
//
//    }
    //从输入流读取post参数
    public String readStreamParameter(ServletInputStream in){
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader=null;
        try{
            reader = new BufferedReader(new InputStreamReader(in));
            String line=null;
            while((line = reader.readLine())!=null){
                buffer.append(line);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(null!=reader){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return buffer.toString();
    }

}
