package com.rootls.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 14-3-3
 * Time: 下午2:38
 * To change this template use File | Settings | File Templates.
 */
public class JiamiJiemi {

    /**
     * 加密方法
     *
     * @param in
     * @return
     */
    public static String jiami(String in,String secretKey) {
        if (in == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return myEncrypt(in + sdf.format(new Date()), secretKey);
    }

    /**
     * 解密方法
     *
     * @param out
     * @return
     */
    public static String jiemi(String out,String secretKey) {
        if (out == null) {
            return null;
        }
        return myDecrypt(out.substring(8, out.length()), secretKey);
    }

    /**
     * 根据传入的加密字符串获得日期
     *
     * @param str
     * @return
     * @throws java.text.ParseException
     */
    public static Date getJiemiDate(String str,String secretKey) throws ParseException {
        if (str == null) {
            return null;
        }
        return new SimpleDateFormat("yyyyMMdd").parse(myDecrypt(str, secretKey).substring(0, 8));
    }


    public static String myEncrypt(String in, String secrectKey) {
        //以字母开头，长度在6-18之间
//        Pattern.compile("^[a-zA-Z]w{5,17}$").matcher(in).matches();
        if (in == null ) {
            return null;
        }
        in = in.trim();

        int num = 1;
        if (secrectKey != null) {
            for (int i = secrectKey.length() - 1; i > 0; i--) {
                num += secrectKey.charAt(i);
            }
            num = num % (secrectKey.length() - 1);
            num = num==0?1:num;
        }

        String out = "";
        //input各位减1
        for (int i = in.length() - 1; i >= 0; i--) {
            if (i == num) {
                out += num;
            }
            if (in.charAt(i) == 0) {
                out += Character.toString((char)127);
            }else if (in.charAt(i) == 65) {
                out += "Z";
            } else if (in.charAt(i) == 97) {
                out += "z";
            } else if (in.charAt(i) == 48) {
                out += "9";
            } else {
                out += Character.toString((char) (in.charAt(i) - 1));
            }

        }

        return out.trim();
    }

    public static String myDecrypt(String out, String secrectKey) {
        //以字母开头，长度在6-18之间
//        Pattern.compile("^[a-zA-Z]w{5,17}$").matcher(out).matches();
        if (out == null ) {
            return null;
        }
        out = out.trim();

        int num = 1;
        if (secrectKey != null) {
            for (int i = secrectKey.length() - 1; i > 0; i--) {
                num += secrectKey.charAt(i);
            }
            num = num % (secrectKey.length() - 1);
            num = num==0?1:num;
        }

        String in = "";
        for (int i = out.length() - 1; i >= 0; i--) {
            if(i==(out.length()-1-1-num)){
                continue;
            }
            if (out.charAt(i) == 127) {
                in += Character.toString((char)0);
            }else if (out.charAt(i) == 57) {
                in  +="0";
            } else if (out.charAt(i) == 90) {
                in  += "A";
            } else if (out.charAt(i) == 122) {
                in  +="a";
            } else {
                in  += Character.toString((char) (out.charAt(i) + 1));
            }

        }
        return in.trim();
    }

    public JiamiJiemi(String secretKey) {
        MyGui tw = new MyGui("加密解密",secretKey);
        tw.setVisible(true);
    }

    public static void main(String[] args) {
        String secretKey = "luowei";
        String in = "AKSZ0349aDYz";
        System.out.println(in);
        //加密
        String out = JiamiJiemi.jiami(in,secretKey);
        System.out.println(out);
        //解密
        in = JiamiJiemi.jiemi(out,secretKey);
        System.out.println(in);

        new JiamiJiemi("luowei");
    }

    //===================================================================

    class MyGui extends JFrame {
        JButton b1 = new JButton("Encrypt");
        JButton b2 = new JButton("Decrypt");
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

        public MyGui(String secretKey) {
            init(secretKey);
        }

        public MyGui(String title,String secretKey) {
            super(title);
            init(secretKey);
        }

        private void init(String secKey) {
            final String secretKey = secKey;
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
            b1.setToolTipText("加密");
            b1.setMnemonic('E');
            b1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    tf2.setText(JiamiJiemi.jiami(tf1.getText()/*,tf3.getText()*/, secretKey));
                    //tf3.setText("");
                }
            });
            b1.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    tf2.setText(JiamiJiemi.jiami(tf1.getText()/*,tf3.getText()*/, secretKey));
                    //tf3.setText("");
                }
            });
            //解密
            b2.setToolTipText("解密");
            b2.setMnemonic('D');
            b2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    tf2.setText(JiamiJiemi.jiemi(tf1.getText()/*,tf3.getText()*/, secretKey));
                    //tf3.setText("");
                }
            });
            b2.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    tf2.setText(JiamiJiemi.jiemi(tf1.getText()/*,tf3.getText()*/, secretKey));
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
