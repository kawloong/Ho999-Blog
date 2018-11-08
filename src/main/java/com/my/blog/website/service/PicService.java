package com.my.blog.website.service;

import com.my.blog.website.model.Vo.PicVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @summery:
 * @Date: 2018/8/31
 * @Time: 11:17
 */

@Service
public class PicService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PicService.class);
    private static final Long PICLIST_FLUSH_SEC = 60L; // 刷新图片目录时间间隔

    @Value("${wechat.upload_path}")
    String picPath;

    List<PicVo> allPics;
    Long initTime = 0L;

    /*
    ** 初始化读出所有图片信息
     */
    public int init() {
        allPics = new ArrayList<PicVo>();
        File file = new File(picPath);
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            if (f.isFile()) {
                allPics.add(new PicVo(f.getName(), f.lastModified()));
            }
        }

        allPics.sort(Comparator.reverseOrder());
        initTime = System.currentTimeMillis() / 1000;
        System.out.println("initTime is " + initTime.toString());
        return allPics.size();
    }

    /*
    ** 获取指定时间内的图片list
     */
    public  List<PicVo> getPicByTime(Long beg, Long end) {
        Long now = System.currentTimeMillis();
        List<PicVo> ret = new ArrayList<>();
        if (now - initTime > PICLIST_FLUSH_SEC) {
            if (init() <= 0) {
                initTime = System.currentTimeMillis();
                return ret;
            }
        }

        for (int i = 0; i < allPics.size(); i++) {
            PicVo itm = allPics.get(i);
            if (itm.getMtime() >= beg && itm.getMtime() < end){
                ret.add(itm);
            }
        }

        return ret;
    }

}
