package com.my.blog.website.controller;

import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @summery:
 * @Date: 2018/8/2
 * @Time: 15:38
 */

@RestController
public class RestTest {

    @Autowired
    private WxMpService wxService;

    private class RValue {
        private String name = "ttt";
        private Integer jjj;
        private Integer idd = 1;

        public Integer getJjj() {
            return jjj;
        }


        public String getName() {
            return name;
        }

        public Integer getIdd() {
            return idd;
        }
    }

    @GetMapping("/hello")
    public RValue hello(){
        System.out.println("/hello request .");
        return new RValue();
    }
}
