package com.rootls.utils;
/**
 * BASE64加密解密
 * User: luowei
 * Date: 14-4-1
 * Time: 下午6:41
 * To change this template use File | Settings | File Templates.
 */

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class BASE64 {

    public static void main(String[] args) {
        String pass = "luowei";
        String jiami =JiamiJiemi.jiami(pass,"luowei");
        String encrypted = encrypt("818930915210baz");
        String decrypted = decrypt("MDk0OTMwOTFoNGR2bnRr");
        String result =  JiamiJiemi.jiemi(decrypted,"luowei");

        System.out.println(pass.equals(result));

        new BASE64();
    }

    public static String encrypt(String key) {
        return (new BASE64Encoder()).encodeBuffer(key.getBytes());
    }

    public static String decrypt(String key) {
        try {
            return new String((new BASE64Decoder()).decodeBuffer(key));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BASE64() {
        MyGui tw = new MyGui("BASE64编码解码");
        tw.setVisible(true);
    }

    class MyGui extends JFrame {
        JButton b1 = new JButton("Encode");
        JButton b2 = new JButton("Decode");
        Panel pan1 = new Panel();
        Panel pan2 = new Panel();
        Panel pan3 = new Panel();
        Panel pan4 = new Panel();
        Panel pan5 = new Panel();
        //
        Label label1 = new Label("Sourse ");
        Label label2 = new Label("target");
//        Label label3 = new Label("Key:");


        TextArea tf1 = new TextArea("Text Area 1", 4, 30, TextArea.SCROLLBARS_VERTICAL_ONLY);
        TextArea tf2 = new TextArea("Text Area 2", 4, 30, TextArea.SCROLLBARS_VERTICAL_ONLY);
        //        TextField tf3 = new TextField("key     ");
        Toolkit theKit = getToolkit();

        public MyGui() {
            init();
        }

        public MyGui(String title) {
            super(title);
            init();
        }

        private void init() {
            setLayout(new BorderLayout());
            pan1.setLayout(new GridLayout(1, 2));
            //
            label1.setBackground(new Color(220, 220, 220));
            label2.setBackground(new Color(220, 220, 220));
//            label3.setBackground(new Color(220,220,220));
            //
//            tf3.setEchoChar('*');
            //

            Dimension wndSize = theKit.getScreenSize();
            setBounds(wndSize.width / 4, wndSize.height / 4,
                    wndSize.width / 2, wndSize.height / 4);
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            //LookAndFeel
            setUndecorated(true);
            getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
            //加密
            b1.setToolTipText("编码");
            b1.setMnemonic('E');
            b1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    tf2.setText(encrypt(tf1.getText()/*,tf3.getText()*/));
                    //tf3.setText("");
                }
            });
            b1.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    tf2.setText(encrypt(tf1.getText()/*,tf3.getText()*/));
                    //tf3.setText("");
                }
            });
            //解密
            b2.setToolTipText("解码");
            b2.setMnemonic('D');
            b2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    tf2.setText(decrypt(tf1.getText()/*,tf3.getText()*/));
                    //tf3.setText("");
                }
            });
            b2.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    tf2.setText(decrypt(tf1.getText()/*,tf3.getText()*/));
                    //tf3.setText("");
                }
            });
            //
            pan1.add(pan2);
            pan1.add(pan3);
            pan2.add(label1);
            pan3.add(label2);
            pan4.add(tf1);
            pan4.add(tf2);
            pan5.add(b1);
            pan5.add(b2);
//            pan5.add(label3);
//            pan5.add(tf3);
            //.
            add("North", pan1);
            add("South", pan5);
            add("Center", pan4);
        }
    }

}
