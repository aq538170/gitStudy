package com.etoak.crawl.link;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * 两个属性： 一个是存放 已经访问的url集合的set  ; 一个是存放待访问url集合的 queue
 */
public class Links {

    // 已访问的 url 集合 已访问过的主要考虑不能重复了使用set来保证不重复
    private static Set visitedUrlSet = new HashSet();

    // 待诘问的 url 集合 待访问的主要考虑 1. 规定访问顺序; 2. 保证不提供重复的带访问 地址
    private static LinkedList unVisitedUrlQueue = new LinkedList();

    // 获得已经访问的URL 数目
    public static int getVisitedUrlNum(){
        return visitedUrlSet.size();
    }

    // 添加到访问过的 URL
    public static void addVisitedUrlSet(String url){
        visitedUrlSet.add(url);
    }

    // 移除访问过的 URL
    public static void removeVisitedUrlSet(String url){
        visitedUrlSet.remove(url);
    }

    // 获得待访问的 URL 集合
    public static LinkedList getUnVisitedUrlQueue(){
        return unVisitedUrlQueue;
    }

    // 添加到待访问的集合中 保证每个 URL 只被访问一次
    public static void addUnvisitedUrlQueue(String url){
        if (null != url && !url.trim().equals("") && !visitedUrlSet.contains(url) && !unVisitedUrlQueue.contains(url)){
            unVisitedUrlQueue.add(url);
        }
    }

    // 删除待访问的 URL
    public static Object removeHeadOfUnVisitedUrlQueue(){
        return unVisitedUrlQueue.remove();
    }

    // 判断未访问的 URL 队列中是否为空
    public static boolean unVisitedUrlQueueIsEmpty(){
        return unVisitedUrlQueue.isEmpty();
    }


}
