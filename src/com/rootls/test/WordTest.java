package com.rootls.test;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 14-3-29
 * Time: 上午1:00
 * To change this template use File | Settings | File Templates.
 */
public class WordTest{

    @Test
    public void testGenWord() throws Exception {

        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");

        // 要填入模本的数据文件
        Map dataMap = new HashMap();
        getData(dataMap);

        // 设置模本装置方法和路径,FreeMarker支持多种模板装载方法。可以重servlet，classpath，数据库装载，
        // ftl文件存放路径
        configuration.setClassForTemplateLoading(this.getClass(), "/resource");

        Template t = null;
        try {
            // test.ftl为要装载的模板
            t = configuration.getTemplate("word-template.ftl");
            t.setEncoding("utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 输出文档路径及名称
        File outFile = new File("d:/test.doc");
        Writer out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        try {
            t.process(dataMap, out);
            out.close();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getData(Map dataMap) {
        dataMap.put("title", "Test");
        dataMap.put("nian", "二零一二");
        dataMap.put("danweiming", "唯为社区");
        List lists = new ArrayList();

        WordBean w1 = new WordBean();
        w1.setPaixu("1");
        w1.setBiaoduan("标段一");
        WordBean w2 = new WordBean();
        w2.setPaixu("2");
        w2.setBiaoduan("标段二");

        lists.add(w1);
        lists.add(w2);

        dataMap.put("wordBeans", lists);
    }

    public static class WordBean {

        public String paixu;
        public String biaoduan;

        public String getPaixu() {
            return paixu;
        }

        public void setPaixu(String paixu) {
            this.paixu = paixu;
        }

        public String getBiaoduan() {
            return biaoduan;
        }

        public void setBiaoduan(String biaoduan) {
            this.biaoduan = biaoduan;
        }
    }
}
