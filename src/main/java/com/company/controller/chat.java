package com.company.controller;

import com.company.domain.Friends;
import com.company.domain.User;
import com.company.service.ClintService;
import com.company.service.daoService.FriendsDaoService;
import com.company.service.daoService.UserDaoService;
import com.company.utils.JsonUtils;
import com.company.utils.MSGUtils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class chat {
    FriendsDaoService friendsDao;
    UserDaoService dao;
    private ClintService cs;
    private JFrame frame;
    private JPanel jpanel;
    private JButton button3;
    private JLabel username;
    private JList list1;
    private JPanel jp1;
    private JScrollPane js1;
    private JPanel jp2;
    private DefaultListModel<String> model;
    private List<friendPanel> panels;
    private String path;

    chat(String username, ClintService cs, FriendsDaoService friendsDao,UserDaoService dao) throws IOException {
        this.model = new DefaultListModel<>();
        panels = new ArrayList<>();
        this.friendsDao = friendsDao;
        this.dao=dao;
        this.cs=cs;
        list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        for(Friends fr:friendsDao.select(username)){
            model.addElement(fr.getFriend());
        }
        list1.setModel(model);
        list1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (list1.getSelectedIndex() != -1) {
                    if (e.getClickCount() == 2) {
                        try {
                            twoClick(list1.getSelectedValue());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    else if(e.isMetaDown()){//检测鼠标右键单击
                        JMenuItem j= new JMenuItem("移除好友");
                        JPopupMenu popupMenu = new JPopupMenu();
                        popupMenu.add(j);
                        popupMenu.show(list1,e.getX(),e.getY());
                        j.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int s = JOptionPane.showConfirmDialog(null, "删除好友将一并删除聊天记录");
                                if(s==0){
                                    String tp = (String) list1.getSelectedValue();
                                    friendsDao.delete(username,tp);
                                    model.remove(list1.getSelectedIndex());
                                    MSGUtils.delete(username,tp);
                                }
                                System.out.println(s);
                            }
                        });
                    }
                }
            }

            private void twoClick(Object value) throws Exception {
                int i = 0;
                for(friendPanel fp:panels){
                    if(fp.getFriend().equals(value)){
                        i=1;
                    }
                }
                if(i==0){
                    friendPanel f = new friendPanel((String) value,username,cs,null,panels,friendsDao,model);
                    panels.add(f);
                    f.run();
                    System.out.println("run over");
                }
            }
        });
        this.username.setText(username);
        String s = JsonUtils.loginF(username, cs.getPort(),cs.getFilePort());
        cs.send(s);

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = JOptionPane.showInputDialog("username:");
                if(s!=null){
                    User user = dao.selectUser(s);
                    if(user!=null&&!username.equals(s)){
                        if(friendsDao.selectF(username,s)==null){
                            Friends friends = new Friends();
                            friends.setUsername(username);
                            friends.setFriend(s);
                            System.out.println(friends);
                            System.out.println(friendsDao);
                            friendsDao.insert(friends);
                            model.addElement(s);
                        }
                    }else {
                        JOptionPane.showMessageDialog(null,"用户名不存在");
                    }
                }

            }
        });
    }
    public void run(){
        frame = new JFrame("chat");
        jpanel.setPreferredSize(new Dimension(200, 400));
        frame.setContentPane(jpanel);
        button3.setOpaque(false);
        button3.setBorder(null);
        jp2.setOpaque(false);
        button3.setIcon(new ImageIcon("src/main/img/f1.png"));
        js1.setOpaque(false);
        js1.getViewport().setOpaque(false);
        jp1.setOpaque(false);
        //jp2.setOpaque(false);
        ((JLabel)list1.getCellRenderer()).setOpaque(false);
        list1.setOpaque(false);
        jpanel.setOpaque(false);
        frame.setContentPane(jpanel);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.getContentPane().add(new MyPanel(new ImageIcon("src/main/img/list.jpg")));
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                String s = JsonUtils.loginF(username.getText(), 30000,30001);
                try {
                    cs.send(s);
                    for(friendPanel friendPanel:panels){
                        friendPanel.closeA();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        Thread fileT = new Thread(()->{
                cs.fileReceive(path);
                path=null;
        });
        fileT.setDaemon(true);

        Thread t = new Thread(()->{
            while (true) {
                try {
                    String str = cs.receive();
                    Map<String, String> json = JsonUtils.getJson(str);
                    int i = 0;
                    if(json.containsKey("ok")&&json.get("ok").equals("确认")){
                        path=json.get("msg");
                        fileT.start();
                    }

                    for(friendPanel fp:panels){
                        if(fp.getFriend().equals(json.get("from"))){
                            i=1;
                            fp.setMSG(str);
                        }
                    }
                    if(i==0){
                        friendPanel f = new friendPanel(json.get("from"),json.get("to"),cs,str,panels,friendsDao,model);
                        panels.add(f);
                        f.run();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //后台线程
        t.setDaemon(true);
        t.start();
    }
}