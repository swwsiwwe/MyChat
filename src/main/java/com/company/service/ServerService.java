package com.company.service;

import com.company.domain.User;
import com.company.service.daoService.UserDaoService;
import com.company.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.swing.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class ServerService {
    @Autowired
    private UserDaoService daoService;
    private DatagramSocket ds;
    private byte[] bt;
    private int port = 30000;
    private Map<String, Integer> portMap;
    private Map<String,Integer> filePortMap;

    public ServerService() throws SocketException {
        portMap = new HashMap<>();
        filePortMap = new HashMap<>();
        daoService = new UserDaoService();
        ds = new DatagramSocket(this.port);
        bt = new byte[1024];
    }

    public String receive() throws IOException {
        Arrays.fill(bt, (byte) 0);
        DatagramPacket rece = new DatagramPacket(bt, 1024);
        ds.receive(rece);
        String str = new String(rece.getData(), 0, rece.getLength(), "UTF-8");
        return str;

    }

    public void send(String str, String ip, int port) throws IOException {
        InetAddress inetAddress = InetAddress.getByName(ip);
        DatagramPacket send = new DatagramPacket(str.getBytes("UTF-8"), str.getBytes("UTF-8").length, inetAddress, port);
        System.out.println("我要发送");
        ds.send(send);
    }

    public void run(String ip, int port,DatagramSocket ds) throws IOException {
        int k = 0;
        byte[] bt = new byte[1024];
        InetAddress inetAddress = InetAddress.getByName(ip);
        System.out.println("服务器开始接收了");
        DatagramPacket rece = new DatagramPacket(bt, 1024);
        int len = 0;
        while (len == 0) {
            //接收数据包
            ds.receive(rece);
            if (k == 0) {
                k = 1;
            }
            len = rece.getLength();
            if (len > 0) {
                ds.send(new DatagramPacket(bt, 1024, inetAddress, port));
                len = 0;//循环接收
            }
        }
        System.out.println("服务器发完了");
    }

    public void run() {
        while (true) {
            try {
                String receive = receive();
                Map<String, String> json = JsonUtils.getJson(receive);
                if (!json.containsKey("username")) {
                    User user = daoService.selectUser(json.get("to"));
                    if (portMap.containsKey(user.getUsername())) {
                        if(json.containsKey("file")){
                            ThreadUtils.exec.execute(()->{
                                try {
                                    DatagramSocket dss = new DatagramSocket(30001);
                                    ThreadUtils.execute(()->{
                                        try {
                                            run(user.getIp(),filePortMap.get(user.getUsername()),dss);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        return null;
                                    },dss);
                                } catch (SocketException e) {
                                    e.printStackTrace();
                                }
                                System.out.println("ServerService传输完成");
                            });
                        }else{
                            ThreadUtils.exec.execute(() -> {
                                try {
                                    send(receive, user.getIp(), portMap.get(user.getUsername()));
                                    String from = json.get("from");
                                    String to = json.get("to");
                                    if(json.get("msg")!=null){
                                        MSGUtils.write(from + to + ".txt", json.get("msg") + " [" + json.get("time") + "]" + " from:" + from + "\n");
                                        MSGUtils.write(to + from + ".txt", json.get("msg") + " [" + json.get("time") + "]" + " from:" + from + "\n");
                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "好友不在线");
                        System.out.println("离线");
                    }
                } else {
                    if (portMap.containsKey(json.get("username"))) {
                        portMap.remove(json.get("username"));
                        filePortMap.remove(json.get("username"));
                    } else
                        portMap.put(json.get("username"), Integer.parseInt(json.get("port")));
                        filePortMap.put(json.get("username"), Integer.parseInt(json.get("filePort")));
                        System.out.println("--username:"+json.get("username")+" port1:"+json.get("port")+" port2:"+json.get("filePort"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}