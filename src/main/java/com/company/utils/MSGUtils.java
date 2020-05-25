package com.company.utils;

import java.io.*;

public class MSGUtils {
    public static void write(String path, String str){
        File file = new File("src/main/MSG/");
        if(!file.exists()){
            file.mkdir();
        }

        try(BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/MSG/"+path,true))){
            bw.write(str);
        }catch (IOException ie){
            ie.printStackTrace();
        }
    }
    public static void delete(String s1,String s2) {
        File file = new File("src/main/MSG/" + s1 + s2 + ".txt");
        file.delete();
    }

    public static String read(String path){
        StringBuilder sb = new StringBuilder();
        String str;
        try (BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/MSG/"+path)))){
            while((str = bf.readLine())!=null){
                sb.append(str+'\n');
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }
}
