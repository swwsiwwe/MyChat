package com.company.controller;

import javax.swing.*;
import java.awt.*;

public class MyPanel extends JPanel {
    ImageIcon imageIcon;
    MyPanel(ImageIcon imageIcon){
        this.imageIcon=imageIcon;
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(imageIcon.getImage(),0,0,getWidth(),getHeight(),imageIcon.getImageObserver());
    }

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.add(new MyPanel(new ImageIcon("src/main/img/list.jpg")));
        f.setVisible(true);
    }
}