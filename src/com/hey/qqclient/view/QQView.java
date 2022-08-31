package com.hey.qqclient.view;

import com.hey.qqclient.service.FileClientService;
import com.hey.qqclient.service.MessageClientService;
import com.hey.qqclient.service.UserClientService;
import com.hey.qqclient.utils.Utility;
import com.hey.qqcommon.Message;

/*
    @author 何恩运
    客户端的菜单界面
*/
@SuppressWarnings({"all"})
public class QQView {

    private boolean loop = true;//控制是否显示菜单
    private String key = "";//接收用户的键盘输入
    private UserClientService userClientService = new UserClientService();//用于登录服务器/注册用户
    private MessageClientService messageClientService = new MessageClientService();//用于私聊/群聊
    private FileClientService fileClientService = new FileClientService();//用于传输文件

    public static void main(String[] args) {
        new QQView().mainMenu();
        System.out.println("客户端退出系统...");
    }

    //显示主菜单
    private void mainMenu() {
        while (loop) {

            System.out.println("==========欢迎登录网络通信系统==========");
            System.out.println("\t\t\t 1 登陆系统");
            System.out.println("\t\t\t 9 退出系统");
            System.out.print("请输入你的选择：");

            key = Utility.readString(1);

            //根据用户的输入来处理不同的逻辑
            switch (key) {
                case "1":
                    System.out.print("请输入用户号：");
                    String userId = Utility.readString(50);
                    System.out.print("请输入密  码：");
                    String pwd = Utility.readString(50);

                    if (userClientService.checkUser(userId, pwd)) {
                        System.out.println("==========欢迎用户 " + userId + " 登陆成功==========");
                        //进入二级菜单
                        while (loop) {
                            System.out.println("\n==========网络通信系统二级菜单(用户：" + userId + ")==========");
                            System.out.println("\t\t\t 1 显示在线用户列表");
                            System.out.println("\t\t\t 2 群发消息");
                            System.out.println("\t\t\t 3 私聊消息");
                            System.out.println("\t\t\t 4 发送文件");
                            System.out.println("\t\t\t 9 退出系统");
                            System.out.printf("请输入你的选择：");
                            key = Utility.readString(1);
                            switch (key) {
                                case "1":
                                    //获取在线用户列表
                                    userClientService.onlineFriendList();
                                    break;
                                case "2":
                                    System.out.print("请输入想对大家说的话：");
                                    String s = Utility.readString(100);
                                    //将消息封装成message对象发送给服务端
                                    messageClientService.sendMessageToAll(s, userId);
                                    break;
                                case "3":
                                    System.out.print("请输入想聊天的用户号(在线)：");
                                    String getterId = Utility.readString(50);
                                    System.out.print("请输入想说的话：");
                                    String content = Utility.readString(100);
                                    //将消息发送给服务器端
                                    messageClientService.sendMessageToOne(content, userId, getterId);
                                    break;
                                case "4":
                                    System.out.print("请输入你想把文件发送给的用户(在线)：");
                                    getterId = Utility.readString(50);
                                    System.out.print("请输入发送文件的路径(形式如d:\\xx.jpg)：");
                                    String src = Utility.readString(100);
                                    System.out.print("请输入文件发送到的路径(形式如d:\\yy.jpg)：");
                                    String dest = Utility.readString(100);
                                    fileClientService.sendFileToOne(src, dest, userId, getterId);
                                    break;
                                case "9":
                                    //调用方法给服务端发送一个退出系统的message
                                    userClientService.logout();
                                    loop = false;
                                    break;
                                default:
                                    System.out.println("输入有误，请重新输入");
                            }
                        }
                    } else {
                        System.out.println("==========登录失败==========");
                    }
                    break;
                case "9":
                    loop = false;
                    break;
                default:
                    System.out.println("输入有误，请重新输入");
            }
        }
    }
}
