package com.rootls.test;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.*;
import com.rootls.crud.finance.Daytip;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 14-3-29
 * Time: 上午12:57
 * To change this template use File | Settings | File Templates.
 */
    public class PdfTest {

    @Test
    public void testGenPdf() throws Exception {
        PdfReader input = new PdfReader(getConfigPath() + "/pdf-template.pdf");
        FileOutputStream output = new FileOutputStream("d:/aaaa.pdf");
        PdfStamper stamper = new PdfStamper(input, output);
        stamper.setFormFlattening(true);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        stamper.getAcroFields().setField("name", "维唯为为aaa");
        stamper.getAcroFields().setField("sex", "男aaa");
        stamper.getAcroFields().setField("age","25");

        stamper.close();
        input.close();
    }


    @Test
    public void testAddContent2Pdf() throws Exception {

        //利用pdf写入器在磁盘上创建一个document
        Document docment = new Document();
        PdfWriter writer = PdfWriter.getInstance(docment, new FileOutputStream("d:/bbbb.pdf"));

        //打这个document并新建一页
        docment.open();
        docment.newPage();

        //利用pdf读入器读取模板
        PdfReader template =new PdfReader(getConfigPath() + "/pdf-template.pdf");

        //把模板template.pdf的第一页写到docment中去
        PdfImportedPage page = writer.getImportedPage(template, 1);
        PdfContentByte cb = writer.getDirectContent();
        cb.addTemplate(page, 0, 0);

        //添加一段文字到docment当中去
        docment.add(
                new Paragraph("Here some text added to the template")
        );
        //Etc, etc

        docment.close();
    }

    private static String getConfigPath() {
        String configFilePath = Daytip.class.getClassLoader().getResource("resource").getPath().substring(1);
        // 判断系统 linux，windows
        if ("\\".equals(File.separator)) {
            configFilePath = configFilePath.replace("%20", " ");
        } else if ("/".equals(File.separator)) {
            configFilePath = "/" + configFilePath.replace("%20", " ");
        }
        return configFilePath;
    }

}
