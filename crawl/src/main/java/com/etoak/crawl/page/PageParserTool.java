package com.etoak.crawl.page;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 提供了 根据选择器来选取元素 属性 等方法
 */
public class PageParserTool {

    /*
     通过选择器来选取页面的
      */
    public static Elements select(Page page, String cssSelector){
        return page.getDoc().select(cssSelector);
    }

    /*
    通过 css选择器来得到指定元素
     */
    public static Element select(Page page, String cssSelector, int index){
        Elements eles = select(page, cssSelector);
        int realIndex = index;
        if (index < 0){
            realIndex = eles.size() + index;
        }
        return eles.get(realIndex);
    }

    /**
     * 获取满足选择器的元素的链接 选择器 cssSelector 必须定位到具体的超链接
     * 例如我们想抽取 id 为 content 的div 中所有超链接，这里就要将 cssSelector 定义
     * 为 div[id=content] a
     * 放入 set 中， 防止重复;
     * @param page
     * @param cssSelector
     * @return
     */
    public static Set<String> getLinks(Page page, String cssSelector){
        Set<String> links = new HashSet<String>();
        Elements es = select(page, cssSelector);
        Iterator iterator = es.iterator();
        while(iterator.hasNext()){
            Element element = (Element) iterator.next();
            if (element.hasAttr("href")){
                links.add(element.attr("src"));
            }else if (element.hasAttr("src")){
                links.add(element.attr("abs:src"));
            }
        }
        return links;
    }


    public static ArrayList<String> getAttrs(Page page, String cssSelector, String attrName){
        ArrayList<String> result = new ArrayList<String>();
        Elements eles = select(page, cssSelector);
        for (Element ele : eles){
            if (ele.hasAttr(attrName)){
                result.add(ele.attr(attrName));
            }
        }
        return result;
    }

}
