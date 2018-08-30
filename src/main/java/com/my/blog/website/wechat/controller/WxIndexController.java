package com.my.blog.website.wechat.controller;

import com.my.blog.website.controller.BaseController;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

/**
 * @summery: 微信引导主页
 * @Date: 2018/8/13
 * @Time: 14:37
 */


@Controller
public class WxIndexController extends BaseController {
    @Autowired
    private WxMpService wxServ;

    @Value("${wechat.upload_path}")
    String picPath;

    @GetMapping("/wechat/index")
    public String thymeleaf1(HttpServletRequest req) throws WxErrorException {
        String appid =  wxServ.getWxMpConfigStorage().getAppId();
        req.setAttribute("appid", appid);
        String url = req.getRequestURL().toString();
        req.setAttribute("cururl", url);
        WxJsapiSignature sign = wxServ.createJsapiSignature(url);
        req.setAttribute("signurl", sign.getUrl());
        req.setAttribute("nonce", sign.getNonceStr());
        req.setAttribute("timestamp", sign.getTimestamp());
        req.setAttribute("sign", sign.getSignature());

        return this.render("wechat_index");
    }

    @ResponseBody
    @PostMapping("/wechat/index")
    public String tellDownImg(HttpServletRequest req) throws IOException, WxErrorException {
        BufferedReader br = req.getReader();

        String str, wholeStr = "";
        while((str = br.readLine()) != null){
            wholeStr += str;
        }

        String[] mediaId = wholeStr.split(", ");
        String media0 = mediaId[0];

        File ips = wxServ.getMaterialService().mediaDownload(media0);
        ips.renameTo(new File(picPath + media0 + ".jpg"));

        System.out.println(wholeStr);
        return media0;
    }
}
