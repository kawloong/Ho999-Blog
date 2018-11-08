package com.my.blog.website.model.Vo;

/**
 * @summery: 图片属性模型
 * @Date: 2018/8/31
 * @Time: 11:22
 */
public class PicVo implements Comparable<PicVo>{
    private String name;
    private Long mtime;

    public PicVo(String name, Long mtime) {
        this.name = name;
        this.mtime = mtime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getMtime() {
        return mtime;
    }

    public void setMtime(Long mtime) {
        this.mtime = mtime;
    }

    @Override
    public int compareTo(PicVo o) {
        if (this.mtime > o.mtime) return 1;
        if (this.mtime < o.mtime) return -1;
        return 0;
    }
}

