package com.my.blog.website.wechat.handler;


import com.my.blog.website.wechat.builder.TextBuilder;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Map;

/**
 * 接收并保存图片
 */
@Component
public class ImgHandler extends AbstractHandler {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Value("${wechat.upload_path}")
  String picPath;

  @Override
  public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                  Map<String, Object> context, WxMpService weixinService,
                                  WxSessionManager sessionManager) {

    String mediaId = "unknow";
    String content = "收到信息内容：";
      try {
          mediaId = wxMessage.getMediaId();
          File file = weixinService.getMaterialService().mediaDownload(mediaId);
          System.out.println("-----------------2");
          if (null != file) {
              file.renameTo(new File(picPath + mediaId + ".jpg"));
          } else {
              content += "mediaDownlod Fail, mediaId=";
          }
      } catch (WxErrorException e) {
          logger.error("WxErrorException at " + e.getMessage());
      }

    //TODO 组装回复消息
    content +=  mediaId;

    return new TextBuilder().build(content, wxMessage, weixinService);

  }

}
