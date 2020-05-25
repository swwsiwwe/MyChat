package com.company.controller;

import com.company.service.ServerService;
import com.company.service.daoService.FriendsDaoService;
import com.company.service.daoService.UserDaoService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        ServerService bean = applicationContext.getBean(ServerService.class);
        FriendsDaoService friendsDao = applicationContext.getBean(FriendsDaoService.class);
        UserDaoService dao = applicationContext.getBean(UserDaoService.class);
        new Thread(new Runnable() {
            @Override
            public void run() {
                bean.run();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                new login(dao,friendsDao).init();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                new login(dao,friendsDao).init();
            }
        }).start();
    }
}