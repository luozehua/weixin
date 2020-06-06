package com.lzh.wx.service.msg;

import com.lzh.wx.constant.MsgType;
import com.lzh.wx.entity.message.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
class MsgServiceTest {

    @Autowired
    private MsgService msgService;

    @Test
    void responseMsg() {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("ToUserName", "toUser");
        requestMap.put("FromUserName", "FromUserName");
        requestMap.put("MsgType", MsgType.TEXT.getType());
        // BaseMsg baseMsg = msgService.responseText(requestMap);
        // BaseMsg baseMsg = msgService.responseImage(requestMap);
        // BaseMsg baseMsg = msgService.responseVoice(requestMap);
        // BaseMsg baseMsg = msgService.responseVideo(requestMap);
        // BaseMsg baseMsg = msgService.responseMusic(requestMap);
        BaseMsg baseMsg = msgService.responseNews(requestMap);
        // XStream xStream = new XStream(new StaxDriver());
        xstream.processAnnotations(TextMsg.class);
        xstream.processAnnotations(ImageMsg.class);
        xstream.processAnnotations(VoiceMsg.class);
        xstream.processAnnotations(VideoMsg.class);
        xstream.processAnnotations(MusicMsg.class);
        xstream.processAnnotations(NewsMsg.class);
        String s = xstream.toXML(baseMsg);
        System.out.println(s);
    }


    private static XStream xstream = new XStream(new XppDriver() {
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // 对所有xml节点的转换都增加CDATA标记
                boolean cdata = true;

                @SuppressWarnings("unchecked")
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }

                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });


    @Test
    void pic2Word(){
        System.out.println(msgService.pic2Word("http://mmbiz.qpic.cn/mmbiz_jpg/ibp2PxeFqZfS2KsXOia92x4LJuaHdtF14H6t8gFJc08rpYIGzvdZE7crnfEJKmqukeceP4ibGDI7AaXLXyPvSY74Q/0"));
    }
    @Test
    void pic2WordV2(){
        System.out.println(msgService.pic2WordV2("http://mmbiz.qpic.cn/mmbiz_jpg/ibp2PxeFqZfS2KsXOia92x4LJuaHdtF14H6t8gFJc08rpYIGzvdZE7crnfEJKmqukeceP4ibGDI7AaXLXyPvSY74Q/0"));
    }
}