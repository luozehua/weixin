package com.lzh.wx.service.file;


import com.alibaba.fastjson.JSONObject;
import com.lzh.wx.service.token.TokenService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class FileService {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TokenService tokenService;

    public String upload(String path, String type) {
        String accessToken = tokenService.getAccessToken();
        File file = new File(path);
        String url = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
        url = url.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);
        try {
            URL urlObj = new URL(url);
            //强制转换为安全连接
            HttpsURLConnection conn = (HttpsURLConnection) urlObj.openConnection();
            conn.setRequestMethod("POST");
            //设置连接的信息
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            //设置请求头信息
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            //数据边界
            String boundary = "-----" + System.currentTimeMillis();
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            conn.setRequestProperty("Content-Length", String.valueOf(file.length()));
            //获取输出流
            OutputStream outputStream = conn.getOutputStream();
            //创建文件输入流
            InputStream inputStream = new FileInputStream(file);

            //第一部分 头部信息
            //准备头部信息
            StringBuilder sb = new StringBuilder();
            sb.append("--");
            sb.append(boundary);
            sb.append("\t\n");
            sb.append("Content-Disposition:form-data;name=\"media\";filename=\"" + file.getName() + "\"\r\n");
            sb.append("Content-Type:application/octet-stream\r\n\r\n");

            System.out.println(sb.toString());
            outputStream.write(sb.toString().getBytes());

            // 第二部分 文件内容
            byte[] b = new byte[1024];
            int len;
            while ((len = inputStream.read(b)) != -1) {
                outputStream.write(b, 0, len);
            }
            //第三部分 尾部消息
            String foot = "\r\n--" + boundary + "--\r\n";
            outputStream.write(foot.getBytes());
            outputStream.flush();
            outputStream.close();
            //读取数据
            InputStream inputStream1 = conn.getInputStream();
            StringBuilder resp = new StringBuilder();
            while ((len = inputStream1.read(b)) != -1) {
                resp.append(new String(b, 0, len));
            }
            return resp.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public String uploadV2(String filePath, String type) throws Exception {
        String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
        String accessToken = tokenService.getAccessToken();
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new IOException("文件不存在！");
        }

        String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);
        URL urlObj = new URL(url);

        //连接
        HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();

        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);

        //请求头
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Charset", "UTF-8");
        //conn.setRequestProperty("Content-Type","multipart/form-data;");

        //设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + BOUNDARY);

        StringBuilder sb = new StringBuilder();
        sb.append("--");
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition:form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");

        byte[] head = sb.toString().getBytes("utf-8");

        //输出流
        OutputStream out = new DataOutputStream(conn.getOutputStream());

        out.write(head);

        //文件正文部分
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();

        //结尾
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");
        out.write(foot);
        out.flush();
        out.close();

        //获取响应
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        String result = null;

        reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line = null;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        if (result == null) {
            result = buffer.toString();
        }
        reader.close();
        JSONObject json = (JSONObject) JSONObject.parse(result);
        System.out.println(json);
        String mediaId = json.getString("media_id");
        return mediaId;

    }
}
