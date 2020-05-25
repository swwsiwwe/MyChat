package com.company.controller;

import com.company.domain.User;
import com.company.service.daoService.UserDaoService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class create {
    private JPanel jPanel;
    private JTextField textField1;
    private JPasswordField textField2;
    private JButton newUser;
    private JPasswordField textField3;
    private JPanel jp;
    create(UserDaoService dao,JFrame f){
        jp.setPreferredSize(new Dimension(300,200));
        JFrame frame = new JFrame("create");
        frame.setContentPane(jp);
        frame.setResizable(false);
        jp.setOpaque(false);
        jPanel.setOpaque(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        jp.add(new MyPanel(new ImageIcon("src/main/img/login.jpg")));
        frame.setVisible(true);
        newUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = textField1.getText();
                String password=textField2.getText();
                String nPassword=textField3.getText();
                if(username!=null&&password!=null&&username.length()!=0&&password.length()!=0&&nPassword!=null&&nPassword.length()!=0){
                    if(password.equals(nPassword)){
                        User user = dao.selectUser(username);
                        if(user!=null){
                            JOptionPane.showMessageDialog(null,"账号已存在","",JOptionPane.ERROR_MESSAGE);
                        }else{
                            user =new User();
                            user.setUsername(username);
                            user.setPassword(password);
                            user.setIp("127.0.0.1");
                            dao.insertUser(user);
                            frame.dispose();
                            f.setVisible(true);
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"两次密码不一致","",JOptionPane.ERROR_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"账号密码不为空","",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}