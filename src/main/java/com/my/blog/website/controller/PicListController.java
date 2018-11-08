package com.my.blog.website.controller;

import com.my.blog.website.model.Vo.PicVo;
import com.my.blog.website.service.PicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @summery:
 * @Date: 2018/8/31
 * @Time: 10:40
 */

@Controller
public class PicListController extends BaseController {

    @Autowired
    private PicService picService;

    @ResponseBody
    @GetMapping(value="/api/pic", produces="application/json;charset=UTF-8")
    public List<PicVo> picList( @RequestParam(defaultValue = "0")Long beg, @RequestParam(defaultValue = "99999999999000") Long end) {
        List<PicVo> ret = picService.getPicByTime(beg, end);
        return  ret;
    }

    @GetMapping("/pichome")
    public String picHome(HttpServletRequest request) {
        List<PicVo> picvos = picService.getPicByTime(0L, 9999999999000L);
        request.setAttribute("picvos", picvos);
        return this.render("pichome");
    }
}
