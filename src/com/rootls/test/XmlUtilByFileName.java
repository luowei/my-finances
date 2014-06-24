package com.rootls.test;

import com.rootls.helper.ReadData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static com.rootls.common.Config.data_daytips;


/**
 * Created with IntelliJ IDEA.
 * User: 佳
 * Date: 14-4-13
 * Time: 下午7:36
 * To change this template use File | Settings | File Templates.
 */
public class XmlUtilByFileName {


    public static List  xmlUtil(String fileName){

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        List beanList = new ArrayList();

        System.out.println("class name: " + dbf.getClass().getName());
      // step 2:获得具体的dom解析器
        DocumentBuilder db = null;
        try {
            Class cls=Class.forName(fileName);
            Method method = null;
            Object obj = null;
            db = dbf.newDocumentBuilder();
            System.out.println("class name: " + db.getClass().getName());
            // step3: 解析一个xml文档，获得Document对象（根结点）
            Document document = db.parse(new File(fileName+".xml"));
            //获得根元素结点
            Element root = document.getDocumentElement();
            NodeList childNodes = root.getChildNodes();
            for(int i = 0;i<childNodes.getLength();i++){
                obj =  cls.newInstance();
                Node  item = childNodes.item(i);
                //NodeList row = item.getChildNodes();
                if(item.getNodeType()==Node.ELEMENT_NODE){
                    for(Node node=item.getFirstChild();node!=null;node=node.getNextSibling()){
                        //Node item1 = row.item(j);
                        String nodeName = node.getNodeName();
                        method = cls.getMethod("set" + nodeName.toUpperCase().substring(0, 1) + nodeName.substring(1, nodeName.length()),String.class);
                        //String value = node.getTextContent();
                        method.invoke(obj,node.getTextContent());
                    }
                }

                beanList.add(obj);
            }

        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

       return beanList;
    }

    public static void main(String[] args){
        XmlUtilByFileName.xmlUtil(ReadData.getConfigPath("resource/data/regex.xml"));
    }
}
