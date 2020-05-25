package com.company.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class ClintService {

    private DatagramSocket ds;
    private DatagramSocket fileDs;
    private byte [] bt;
    private String server_ip;
    /*自己的端口*/
    private int port;
    private int filePort;
    private int server_port;
    public ClintService(String server_ip,int server_port) throws IOException {
        this.server_ip=server_ip;
        this.server_port = server_port;
        ds = new DatagramSocket();
        fileDs = new DatagramSocket();
        this.port=ds.getLocalPort();
        this.filePort = fileDs.getLocalPort();
        bt = new byte[1024];
    }

    public int getPort() {
        return port;
    }

    public int getFilePort() {
        return filePort;
    }

    public String receive() throws IOException {
        System.out.println(port);
        Arrays.fill(bt,(byte) 0);
        DatagramPacket rece = new DatagramPacket(bt,1024);
        ds.receive(rece);
        String str = new String(rece.getData(),0,rece.getLength(),"UTF-8");
        return str;
    }

    /**
     * @param str 好友ip + MSG
     * @throws IOException
     */
    public void send(String str)throws IOException{
        InetAddress inetAddress = InetAddress.getByName(server_ip);
        DatagramPacket send = new DatagramPacket(str.getBytes("UTF-8"),str.getBytes("UTF-8").length, inetAddress,server_port);
        ds.send(send);
    }

    public void fileSend(String path) throws IOException{
        byte[]bt = new byte[1024];
        InetAddress inetAddress = InetAddress.getByName(server_ip);
        try(FileInputStream in = new FileInputStream(path)){
            TimeUnit.MILLISECONDS.sleep(1000);
            System.out.println("我开始发了");
            while((in.read(bt))!=-1){
                DatagramPacket send = new DatagramPacket(bt,bt.length, inetAddress,30001);
                fileDs.send(send);
                TimeUnit.MILLISECONDS.sleep(10);
            }
            System.out.println("我发完了");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void fileReceive(String path){
            System.out.println("port:::"+fileDs.getLocalPort());
            byte[] btt = new byte[1024];
            try(FileOutputStream out = new FileOutputStream("src/main/TEMP/"+path)){
                System.out.println(path+" this path");
                DatagramPacket rec = new DatagramPacket(btt, 1024);
                int len = 0;
                while (len == 0) {
                    //接收数据包
                    fileDs.receive(rec);
                    len = rec.getLength();
                    if (len > 0) {
                        out.write(btt,0,len);
                        len = 0;//循环接收
                    }
                }
                System.out.println("接收完成");
            }catch (Exception e){

            }

    }

    public void close(){
        ds.close();
    }

}