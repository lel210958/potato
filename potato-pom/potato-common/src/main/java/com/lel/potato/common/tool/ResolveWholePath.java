package com.lel.potato.common.tool;

import cn.hutool.core.io.FileUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class ResolveWholePath {
    private static CloseableHttpClient httpClient = HttpClients.createDefault();

    public static void start(String url, String targetFileName){
        System.err.println("start download");
        String path = downloadM3U8(url);
        try {
            writeTS(path, targetFileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("the download task has finished: " + path);
    }

    /**
     * 下载m3u8文件
     * */
    private static String downloadM3U8(String url){
        //创建http请求
        HttpUriRequest uri = new HttpGet(url);
        try {
            //执行http请求
            CloseableHttpResponse response = httpClient.execute(uri);
            HttpEntity entity = response.getEntity();
            //获取http响应
            InputStream stream = entity.getContent();
            //取今天时间
            LocalDate today = LocalDate.now();
            String format = today.format(DateTimeFormatter.ISO_DATE);
            File file = new File("D:\\oss" + File.separator + format);
            //如果路径不存在
            if (!file.exists()) {
                //级联创建文件路径
                file.mkdirs();
            }
            //定义m3u8生成文件全路径
            String path = file.getAbsolutePath() + File.separator + UUID.randomUUID() + ".m3u8";
            //写文件
            FileUtil.writeFromStream(stream, new File(path));
            return path;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeTS(String path, String targetFileName) throws FileNotFoundException {
        File file = new File(path);
        if(!file.exists()){
            return;
        }
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        //初始化视频文件
        File target = new File(file.getParent() + File.separator + targetFileName + ".ts");
        try (OutputStream fos = Files.newOutputStream(target.toPath());
             BufferedOutputStream bos = new BufferedOutputStream(fos)){
            FileUtil.readLines(randomAccessFile, Charset.defaultCharset(), line -> {
                if(!line.startsWith("#")){
                    try {
                        //如果当前段是视频地址
                        System.err.println(line);
                        CloseableHttpResponse response = httpClient.execute(new HttpGet(line));
                        HttpEntity entity = response.getEntity();
                        //写视频文件
                        entity.writeTo(bos);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
