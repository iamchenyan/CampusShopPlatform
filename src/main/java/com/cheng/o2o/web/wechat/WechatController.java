package com.cheng.o2o.web.wechat;

import com.cheng.o2o.util.wechat.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author cheng
 *         2018/4/13 19:27
 */
@Controller
@RequestMapping("wechat")
public class WechatController {

    private static Logger logger = LoggerFactory.getLogger(WechatController.class);

    @GetMapping()
    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        logger.debug("weChat get...");

        // 微信加密签名，signature 结合了开发者填写的token参数和请求中的timestamp参数、nonce参数
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        // 通过检验 signature 对请求进行校验，若校验成功则原样返回 echostr，表示接入成功，否则接入失败
        PrintWriter out = null;
        try {
            out = response.getWriter();
            if (SignUtil.checkSignature(signature, timestamp, echostr)) {
                logger.debug("wechat get success...");
                out.print(echostr);
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.debug("wechat get fail...");
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
