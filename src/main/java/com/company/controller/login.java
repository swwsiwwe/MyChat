package com.company.controller;

import com.company.domain.User;
import com.company.service.ClintService;
import com.company.service.daoService.FriendsDaoService;
import com.company.service.daoService.UserDaoService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class login {
    private UserDaoService dao;
    private FriendsDaoService friendsDao;
    private JPanel jPanel;
    private JTextField textField1;
    private JPasswordField textField2;
    private JButton button;
    private JButton newUser;
    private JPanel jp;

    void init(){
        jPanel.setPreferredSize(new Dimension(300,200));
        JFrame frame = new JFrame("login");
        frame.setContentPane(jPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        ImageIcon img = new ImageIcon("src/main/img/login.jpg");
        frame.setResizable(false);
        jPanel.setOpaque(false);
        jp.setOpaque(false);
        frame.getContentPane().add(new MyPanel(img));
        frame.setVisible(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = textField1.getText();
                String password= textField2.getText();
                if(username==null||password==null||username.length()==0||password.length()==0){
                    JOptionPane.showMessageDialog(null,"账号密码不为空","",JOptionPane.ERROR_MESSAGE);
                }else{
                    User user = dao.selectUser(username);
                    if(user==null||!user.getPassword().equals(password)){
                        JOptionPane.showMessageDialog(null,"账号密码错误","",JOptionPane.ERROR_MESSAGE);
                    }else{
                        frame.dispose();
                        try {
                            new chat(username,new ClintService("127.0.0.1",30000),friendsDao,dao).run();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
        newUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                new create(dao,frame);
            }
        });
    }

    login(UserDaoService dao,FriendsDaoService friendsDao) {
        this.dao = dao;
        this.friendsDao = friendsDao;
    }
}
