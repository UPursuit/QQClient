package com.hey.qqclient.service;

import java.util.HashMap;

/*
    @author 何恩运
    该类用于管理客户端连接到服务器端的线程的类
*/
@SuppressWarnings({"all"})
public class ManageClientConnectServerThread {
    //把多个线程放入一个HashMap集合，key就是用户id，value就是线程
    private static HashMap<String, ClientConnectServerThread> hm = new HashMap<>();

    //将某个线程加入到集合
    public static void addClientConnectServerThread(String userId, ClientConnectServerThread clientConnectServerThread) {
        hm.put(userId, clientConnectServerThread);
    }

    //通过userId可以得到对应线程
    public static ClientConnectServerThread getClientConnectServerThread(String userId) {
        return hm.get(userId);
    }
}
