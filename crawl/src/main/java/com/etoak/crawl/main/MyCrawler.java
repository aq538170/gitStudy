package com.etoak.crawl.main;

import com.etoak.crawl.link.LinkFilter;
import com.etoak.crawl.link.Links;
import com.etoak.crawl.page.Page;
import com.etoak.crawl.page.PageParserTool;
import com.etoak.crawl.page.RequestAndResponseTool;
import com.etoak.crawl.util.FileTool;
import org.jsoup.select.Elements;

import java.util.Set;

public class MyCrawler {

    /**
     * 使用种子初始化  URL 队列
     * @param seeds
     */
    private void initCrawlerWithSeeds(String[] seeds){
        for (int i = 0; i < seeds.length; i++ ){
            Links.addUnvisitedUrlQueue(seeds[i]);
        }
    }

    /**
     * 抓取过程
     * @param seeds
     */
    public void crawling(String[] seeds){

        // 初始化 URL 队列
        initCrawlerWithSeeds(seeds);

        // 定义过滤器， 提取以 http://www.baidu.com 开关的链接
        LinkFilter filter = new LinkFilter() {
            public boolean accept(String url) {
                return true;
//                if (url.startsWith("http://www.baidu.com")){
//                if (url.startsWith("http://www.nipic.com")){
//                    return true;
//                } else {
//                    return false;
//                }
            }
        };

        // 循环条件: 待抓取的链接不空且抓取的网页不多于1000
        while(!Links.unVisitedUrlQueueIsEmpty() && Links.getVisitedUrlNum() <= 10000){
            // 先从待访问序列中取出第一个；
            String visiUrl = (String) Links.removeHeadOfUnVisitedUrlQueue();
            if (null == visiUrl){
                continue;
            }

            // 根据 URL 得到 page;
            Page page = RequestAndResponseTool.sendRequestAndGetResponse(visiUrl);

            // 根据 page 进行处理: 访问 DOM 的某个标签
            Elements es = PageParserTool.select(page, "a");
            if (!es.isEmpty()){
                System.out.println("下面将打印所有a标签：");
                System.out.println(es);
            }

            // 将文件保存
            FileTool.saveToLocal(page);

            // 将已经访问过的链接放入已访问的链接中
            Links.addVisitedUrlSet(visiUrl);

            // 得到超链接
            Set<String> links = PageParserTool.getLinks(page,"img");
            for (String link :links){
                Links.addUnvisitedUrlQueue(link);
                System.out.println("新增爬取路径" + link);
            }
        }
    }

    // main 方法入口
    public static void main(String[] args){
        MyCrawler crawler = new MyCrawler();
        crawler.crawling(new String[]{"http://www.gzwrit.com"});
    }
}
