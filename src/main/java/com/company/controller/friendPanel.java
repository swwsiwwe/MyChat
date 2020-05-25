package com.company.controller;

import com.company.domain.Friends;
import com.company.service.ClintService;
import com.company.service.daoService.FriendsDaoService;
import com.company.utils.JsonUtils;
import com.company.utils.Time;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.List;
import java.util.Map;

public class friendPanel {
    private ClintService cs;
    private JFrame jFrame;
    private JButton button;
    private JTextField textField1;
    private JTextArea textArea1;
    private JPanel jpanel;
    private JPanel jp;
    private JPanel jp1;
    private JScrollPane js1;
    private JPanel jp2;
    private JPanel jp4;
    private JLabel username;
    private JButton msgButton;
    private JButton sendFileButton;
    private JButton addFriend;
    private String friend;
    private List<friendPanel> list;
    private String sendPath;
    private String my;

    public String getFriend() {
        return friend;
    }

    public friendPanel(String username, String user, ClintService cs, String msg, List<friendPanel> list, FriendsDaoService dao,DefaultListModel model) throws Exception{
        this.list=list;
        this.cs=cs;
        friend =username;
        this.username.setText(username);
        textArea1.setEnabled(false);
        textArea1.setLineWrap(true);
        textArea1.setSize(500,500);
        textArea1.setFont(new Font("宋体",Font.PLAIN,14));
        if(msg!=null){
            Map<String, String> json = JsonUtils.getJson(msg);
            textArea1.append("["+json.get("time")+"]");
            textArea1.append(username+":\n"+json.get("msg")+"\n\n");
        }

        button.addActionListener(e-> {
                String s1 = textField1.getText();
                if(s1==null||s1.length()==0){
                    JOptionPane.showMessageDialog(null,"发送不为空","warning",1);
                }else{
                    textField1.setText("");
                    textArea1.append("["+Time.getTime()+"]");
                    textArea1.append(user+":\n"+s1+"\n\n");
                    try {
                        cs.send(JsonUtils.setJson(user,username,s1,null,null));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
        });

        msgButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    File file = new File("src/main/MSG/"+user+username+".txt");
                    try {
                        Desktop.getDesktop().open(file);
                    } catch (IllegalArgumentException | IOException ie){
                        JOptionPane.showMessageDialog(null,"暂无聊天记录,快去聊天吧！");
                    }

            }
        });
        addFriend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Friends f= dao.selectF(user,username);
                if(f==null){
                    Friends f1 = new Friends();
                    f1.setUsername(user);
                    f1.setFriend(username);
                    dao.insert(f1);
                    model.addElement(f1.getFriend());
                    JOptionPane.showMessageDialog(null,"保存成功");
                }else{
                    JOptionPane.showMessageDialog(null,"已经是好友了");
                }
            }
        });
        sendFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser j = new JFileChooser();
               // j.setFileSelectionMode(JFileChooser.D);
                j.setDialogTitle("选择路径...");
                int acc = j.showDialog(null, "发送");
                if(acc==JFileChooser.APPROVE_OPTION){
                    String path = j.getCurrentDirectory()+"\\"+j.getSelectedFile().getName();
                    System.out.println(path);
                    int ok = JOptionPane.showConfirmDialog(null, "确定发送:"+path+"?");
                    System.out.println(ok);
                    if(ok==0){
                        //发送
                        try {
                            cs.send(JsonUtils.setJson(user,username,"发送文件"+j.getSelectedFile().getName(),null,null));
                            textArea1.append("["+Time.getTime()+"]");
                            textArea1.append(user+":\n"+"发送文件"+j.getSelectedFile().getName()+"\n\n");
                            //first
                            cs.send(JsonUtils.setJson(user,username,j.getSelectedFile().getName(),null,"确认"));
                            sendPath=path;
                            my=user;
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }else{
                        System.out.println("取消");
                    }
                }
            }
        });
    }
    public void setMSG(String str){
        try {
            Map<String, String> json = JsonUtils.getJson(str);
            if(json.containsKey("ok")) {
                if (json.get("msg") == null) {
                    //forth
                    System.out.println("forth");
                    cs.send(JsonUtils.setJson(my,friend,"file",sendPath,null));
                    cs.fileSend(sendPath);


                }else{
                    System.out.println("second");
                    //second
                    int ok = JOptionPane.showConfirmDialog(null, "是否接收" + json.get("msg"));
                    if (ok == 0) {
                        System.out.println("third");
                        //third
                        cs.send(JsonUtils.setJson(json.get("to"), json.get("from"), null, null, json.get("msg")));
                    }
                }
            }else {
                textArea1.append("["+json.get("time")+"]");
                textArea1.append(json.get("from")+":\n"+json.get("msg")+"\n\n");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run(){
        button.setBorder(null);
        button.setIcon(new ImageIcon("src/main/img/send.png"));
        button.setOpaque(false);
        jFrame = new JFrame("chat");
        ImageIcon img = new ImageIcon("src/main/img/back2.jpg");
        jpanel.setPreferredSize(new Dimension(600,500));
        jFrame.setContentPane(jpanel);
        jp.setOpaque(false);
        jpanel.setOpaque(false);
        jp1.setOpaque(false);
        jp2.setOpaque(false);
        jp4.setOpaque(false);
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.pack();
        jFrame.setResizable(false);
        jFrame.getContentPane().add(new MyPanel(img));
        jFrame.setVisible(true);
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });
    }
    public void close(){
        jFrame.dispose();
        list.remove(this);
    }

    public void closeA(){
        jFrame.dispose();
    }
}