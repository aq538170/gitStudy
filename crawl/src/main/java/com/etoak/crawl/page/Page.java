package com.etoak.crawl.page;

import com.etoak.crawl.util.CharsetDetector;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.UnsupportedEncodingException;

/**
 * 保存响应的相关内容 对外提供访问方法
 */
public class Page {

    private byte[] content;
    // 网页源码字符串
    private String html;
    //网页Dom文档
    private Document doc;
    // 字符编码
    private String charset;
    // url 路径
    private String url;
    // 内容类型
    private String contentType;

    public Page(){}

    public Page(byte[] content, String url, String contentType){
        this.content = content;
        this.url = url;
        this.contentType = contentType;
    }

    public String getCharset(){
        return charset;
    }

    public String getUrl(){
        return url;
    }

    public String getContentType(){
        return contentType;
    }

    public byte[] getContent(){
        return content;
    }

    /**
     * 返回网页源码字符串
     * @return html
     */
    public String getHtml(){
        if (null != html){
            return html;
        }
        if (null == content){
            return null;
        }
        if (null == charset){
//            charset = CharsetDetector.guessEncoding(content);
            charset = CharsetDetector.guessEncoding(content);

        }
        try {
            this.html = new String(content, charset);
            return html;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 得到文档
     * @return
     */
    public Document getDoc(){
        if (null != doc){
            return doc;
        }
        try {
            this.doc = Jsoup.parse(getHtml(), url);
            return doc;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
