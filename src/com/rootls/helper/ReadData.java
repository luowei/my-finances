package com.rootls.helper;

import com.rootls.crud.account.Account;
import com.rootls.crud.finance.Daytip;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.rootls.common.Config.*;
import static com.rootls.utils.CompressUtil.unzipSelectFile2InputStream;
import static com.rootls.utils.JiamiJiemi.jiami;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 14-3-14
 * Time: 下午5:01
 * To change this template use File | Settings | File Templates.
 */
@Component
public class ReadData {

    Logger logger = LoggerFactory.getLogger(ReadData.class);

    /**
     * 从文件中读取Daytip
     * @return
     */
    public synchronized List<Daytip> readTipsFromFile() {
        InputStreamReader read = null;
        BufferedReader reader = null;
        try {
            File file = new File(getConfigPath(data_daytips));
            List<Daytip> list = new ArrayList<Daytip>();
            read = new InputStreamReader(new FileInputStream(file), encoding);
            reader = new BufferedReader(read);
            String line;
            while ((line = reader.readLine()) != null) {

                if (isNotBlank(line) && line.contains("~")) {
                    String[] arr = line.split("\\~");
                    Daytip tip = new Daytip(arr[0], null, arr[1], Float.valueOf(arr[2]), arr[3]);
                    tip.setType(arr[4]);
                    list.add(tip);
                }
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(),e);
        } finally {
            close(read);
            close(reader);
        }
    }

    /**
     * 从文件中读取Daytip
     *
     * @return
     */
    public synchronized List<Account> readAccountsFromZipFile(String secrectKey) {
        InputStreamReader read = null;
        BufferedReader reader = null;
        try {
            String zipFilePath = ReadData.getConfigPath(data_accountZip);
            read = new InputStreamReader(unzipSelectFile2InputStream(zipFilePath, data_accountFileName, zipPassword), encoding);
            reader = new BufferedReader(read);
            List<Account> list = new ArrayList<Account>();

            String line;
            while ((line = reader.readLine()) != null) {

                if (isNotBlank(line) && line.contains(":")) {
                    String[] arr = line.split("\\:");
                    if(arr[1] != null && arr[1].contains("/")){
                        String[] arr2 =  arr[1].split("\\/");
                        Account account = new Account(null, arr[0], arr2[0], jiami(arr2[1], secrectKey));
                        list.add(account);
                    }

                }
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(),e);
        } finally {
            close(read);
            close(reader);
        }
    }


    public static String getConfigPath(String fileName) {
        String configFilePath = ReadData.class.getClassLoader().getResource(fileName).getPath().substring(1);
        // 判断系统 linux，windows
        if ("\\".equals(File.separator)) {
            configFilePath = configFilePath.replace("%20", " ");
        } else if ("/".equals(File.separator)) {
            configFilePath = "/" + configFilePath.replace("%20", " ");
        }
        return configFilePath;
    }

    public static Boolean close(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(),e);
        }
    }


    //======================================================================================================

    //------------------------
    public static String url = "http://note.youdao.com/yws/public/note/c316d3c1acc0452d8cd6d5d599824e13?keyfrom=public";
    public synchronized List<Daytip> readTipsFromUrl() {
        InputStreamReader read = null;
        BufferedReader reader = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            TipData tipData = mapper.readValue(new URL(url), TipData.class);

            List<Daytip> list = new ArrayList<Daytip>();
            String[] lines = tipData.getContent().replace("<div>","").replace("\r","")
                    .replace("\n","").replace("</div>","\n").split("\n");

            for(String line:lines){
                if(isNotBlank(line) && line.contains("~")){
                    String[] arr = line.split("\\~");
                    Daytip tip = new Daytip(arr[0],null,arr[1],Float.valueOf(arr[2]),arr[3]);
                    tip.setType(arr[4]);
                    list.add(tip);
                }
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            close(read);
            close(reader);
        }
    }


    public static void main(String[] args) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        TipData tipData = mapper.readValue(new URL(url), TipData.class);

        List<Daytip> list = new ArrayList<Daytip>();
        String[] lines = tipData.getContent().replace("<div>","").replace("</div>","").split("\n");

        for(String line:lines){

            if(isNotBlank(line) && line.contains("~")){
                String[] arr = line.split("\\~");
                Daytip tip = new Daytip(arr[0],null,arr[1],Float.valueOf(arr[2]),arr[3]);
                list.add(tip);
            }
        }
        System.out.println(list.size());
    }

    public List readBeansFromZipFile(String type, String secretKey) {
        return null;
    }

    public static class TipData implements Serializable{
        //标题
        String tl;
        //内容
        String content;

        Long mt,ct,sz;
        String p,su,au;

        public String getTl() {
            return tl;
        }

        public void setTl(String tl) {
            this.tl = tl;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Long getMt() {
            return mt;
        }

        public void setMt(Long mt) {
            this.mt = mt;
        }

        public Long getCt() {
            return ct;
        }

        public void setCt(Long ct) {
            this.ct = ct;
        }

        public Long getSz() {
            return sz;
        }

        public void setSz(Long sz) {
            this.sz = sz;
        }

        public String getP() {
            return p;
        }

        public void setP(String p) {
            this.p = p;
        }

        public String getSu() {
            return su;
        }

        public void setSu(String su) {
            this.su = su;
        }

        public String getAu() {
            return au;
        }

        public void setAu(String au) {
            this.au = au;
        }
    }

}
