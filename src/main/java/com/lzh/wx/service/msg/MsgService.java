package com.lzh.wx.service.msg;

import com.alibaba.fastjson.JSONObject;
import com.baidu.aip.ocr.AipOcr;
import com.lzh.wx.entity.message.*;
import com.lzh.wx.service.token.TokenService;
import com.lzh.wx.utils.OkHttpUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import okhttp3.Headers;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.ServletInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MsgService {


    @Value("${baidu.app.id}")
    private String baiduAppId;

    @Value("${baidu.app.key}")
    private String baiduAppKey;

    @Value("${baidu.app.secret}")
    private String baiduAppSecret;


    @Autowired
    private TokenService tokenService;

    /**
     * 获取接受到的消息
     *
     * @param ips
     * @return
     */
    public Map<String, String> getMsg(ServletInputStream ips) {
        Map<String, String> map = readXml(ips);
        return map;
    }


    /**
     * 解析XML
     *
     * @param ips
     * @return
     */
    private Map<String, String> readXml(InputStream ips) {
        Map<String, String> result = new HashMap<>();
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(ips);
            //3.获取根节点
            Element root = document.getRootElement();
            List<Element> elements = root.elements();
            for (Element element : elements) {
                result.put(element.getName(), element.getStringValue());
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 消息回复
     *
     * @param requestMap
     * @return
     */
    public String responseMsg(Map<String, String> requestMap) {
        String msgType = requestMap.get("MsgType");
        BaseMsg msg = null;
        switch (msgType) {
            case "text":
                msg = responseText(requestMap);
                break;
            case "image":
                msg = responseImage(requestMap);
                break;
            case "voice":
                msg = responseVoice(requestMap);
                break;
            case "video":
                msg = responseVideo(requestMap);
                break;
            case "music":
                msg = responseMusic(requestMap);
                break;
            case "news":
                msg = responseNews(requestMap);
                break;
            case "event":
                msg = dealEvent(requestMap);
                break;
        }
        return bean2XML(msg);
    }

    /**
     * 处理时间推送
     *
     * @param requestMap
     * @return
     */
    private BaseMsg dealEvent(Map<String, String> requestMap) {
        String event = requestMap.get("Event");
        BaseMsg msg = null;
        switch (event) {
            //订阅
            case "subscribe":
                break;
            //取消订阅
            case "unsubscribe":
                break;
            case "SCAN":
                break;
            case "LOCATION":
                break;
            case "CLICK":
                msg = dealClickEvent(requestMap);
                break;
            case "VIEW":
                break;
            default:
                break;
        }
        return msg;
    }

    private BaseMsg dealClickEvent(Map<String, String> requestMap) {
        String eventKey = requestMap.get("EventKey");
        BaseMsg msg = null;
        switch (eventKey) {
            case "1":
                msg = responseText(requestMap);
                break;
            case "31":

                break;
            case "32":
                msg = responseNews(requestMap);
                break;
        }
        return msg;
    }

    private String bean2XML(BaseMsg msg) {
        xstream.processAnnotations(TextMsg.class);
        xstream.processAnnotations(ImageMsg.class);
        xstream.processAnnotations(VoiceMsg.class);
        xstream.processAnnotations(VideoMsg.class);
        xstream.processAnnotations(MusicMsg.class);
        xstream.processAnnotations(NewsMsg.class);
        return xstream.toXML(msg);
    }

    /**
     * 处理图文
     *
     * @param requestMap
     * @return
     */
    public BaseMsg responseNews(Map<String, String> requestMap) {
        NewsMsg newsMsg = new NewsMsg(requestMap);
        newsMsg.setArticleCount("1");
        Article article = new Article();
        article.setTitle("争当全面的优等生 第三代名爵6静态体验");
        article.setDescription("当2017年第二代名爵6面世的时候，我相信不少年轻人都为之动心，毕竟在当年能够拥有如此运动范儿的车型并不多见，出色的销量也证明了名爵6这款车的成功。而三年过去了，市面上的运动型车越来越多，争强好胜的名爵6不得不做出一些改变，正因如此，第三代名爵6来到了我们面前，今天我们就来看看它究竟有何能耐。");
        article.setPicUrl("http://15e702i402.51mypc.cn/images/4198189.jpg");
        article.setUrl("https://news.58che.com/guide/2234415.html");
        Articles articles = new Articles(Arrays.asList(article));
        newsMsg.setArticles(articles);
        return newsMsg;
    }

    /**
     * 处理音乐消息
     *
     * @param requestMap
     * @return
     */
    public BaseMsg responseMusic(Map<String, String> requestMap) {
        MusicMsg musicMsg = new MusicMsg(requestMap);
        Music music = new Music();
        music.setTitle("title");
        music.setDescription("decription");
        music.setHQMusicUrl("hqurl");
        music.setMusicUrl("url");
        music.setThumbMediaId("mid");
        musicMsg.setMusic(music);
        return musicMsg;
    }

    /**
     * 处理视频消息
     *
     * @param requestMap
     * @return
     */
    public BaseMsg responseVideo(Map<String, String> requestMap) {
        VideoMsg videoMsg = new VideoMsg(requestMap);
        Video video = new Video();
        video.setTitle("videoTitle");
        video.setDescription("description");
        video.setMediaId("mId");
        videoMsg.setVideo(video);
        return videoMsg;
    }

    /**
     * 处理语音消息
     *
     * @param requestMap
     * @return
     */
    public BaseMsg responseVoice(Map<String, String> requestMap) {
        VoiceMsg voiceMsg = new VoiceMsg(requestMap);
        Voice voice = new Voice();
        voice.setMediaId("mId");
        voiceMsg.setVoice(voice);
        return voiceMsg;
    }

    /**
     * 处理图片消息(图片是识别)
     *
     * @param requestMap
     * @return
     */
    public BaseMsg responseImage(Map<String, String> requestMap) {
//        Image image = new Image();
//        image.setMediaId("mId");
//        imageMsg.setImage(image);

        String picUrl = requestMap.get("PicUrl");
        String wordV2 = pic2WordV2(picUrl);
        TextMsg textMsg = new TextMsg(requestMap);
        textMsg.setContent(wordV2);
        return textMsg;
    }


    public String pic2Word(String imageUrl) {
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic";
        //获取token
        String baiduAccessToken = tokenService.getBaiduAccessToken();
        JSONObject param = new JSONObject();
        param.put("access_token", baiduAccessToken);
        param.put("url", imageUrl);
        try {
            Headers headers = Headers.of("Content-Type", "application/x-www-form-urlencoded");
            String data = OkHttpUtil.getInstance().postJsonWithHeader(url, JSONObject.toJSONString(param), headers);
            JSONObject dataJson = JSONObject.parseObject(data);
            return JSONObject.toJSONString(dataJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String pic2WordV2(String imageUrl) {


        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<>();
        options.put("detect_direction", "true");
        options.put("detect_language", "true");


        // 初始化一个AipOcr
        AipOcr client = new AipOcr(baiduAppId, baiduAppKey, baiduAppSecret);

        // 可选：设置网络连接参数
//        client.setConnectionTimeoutInMillis(2000);
//        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
        // System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

        // 调用接口
        org.json.JSONObject res = client.webImageUrl(imageUrl, options);
        JSONArray words_result = res.getJSONArray("words_result");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words_result.length(); i++) {
            String words = words_result.getJSONObject(i).getString("words");
            sb.append(words).append("  ");
        }
        return sb.toString();
    }

    /**
     * 处理文本消息
     *
     * @param requestMap
     * @return
     */
    public BaseMsg responseText(Map<String, String> requestMap) {
        TextMsg textMsg = new TextMsg(requestMap);
        String content = requestMap.get("Content");
        if ("图文".equals(content)) {
            NewsMsg newsMsg = new NewsMsg(requestMap);
            newsMsg.setArticleCount("1");
            Article article = new Article();
            article.setTitle("毒舌竹叶青");
            article.setDescription("毒蛇竹叶青介绍，其毒无比!");
            article.setPicUrl("http://15e702i402.51mypc.cn/images/tec.jpg");
            article.setUrl("www.baidu.com");
            Articles articles = new Articles(Arrays.asList(article));
            newsMsg.setArticles(articles);
            return newsMsg;
        } else {
            textMsg.setContent("你真的是太坏了");
            return textMsg;
        }
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
}

