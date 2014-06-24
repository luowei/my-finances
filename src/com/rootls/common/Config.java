package com.rootls.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 13-1-11
 * Time: 下午1:56
 * To change this template use File | Settings | File Templates.
 */
public class Config implements ServletContextListener {

    public static String properties_fileName = getConfigPath();

    public static String login_success = "登录成功";
    public static String login_faild = "登录失败";
    public static String authentication_faild = "验证失败";

    public static Integer pageSize = 20;
    public static Long inerCache_expire = 24 * 3600 * 1000L;

    public static String zipPassword="luowei505050";
    public static String data_daytips = "resource/data/daytips.txt";
    public static String data_accountZip="resource/data/accounts.zip";
    public static String data_accountFileName="accounts.txt";
    public static String encoding="UTF-8";
    public static String date_pattern = "yyyy-MM-dd";
    public static String time_pattern = "yyyy-MM-dd HH:mm:dd";
    public static String secret_key = "luowei";
    public static String ctx = "/";

    private void setFields(Map<String, String> map) {
        String _login_success = map.get("login_success");
        login_success = (_login_success != null && _login_success !="")?_login_success:login_success;

        String _login_faild = map.get("login_faild");
        login_faild = (_login_faild != null && _login_faild !="")?_login_faild:login_faild;

        String _authentication_faild = map.get("authentication_faild");
        authentication_faild = (_authentication_faild != null && _authentication_faild !="")?_authentication_faild:authentication_faild;

        Integer _pageSize = Integer.valueOf(map.get("pageSize"));
        pageSize = (_pageSize != null && _pageSize >= 0) ? _pageSize : pageSize;

        Long _inerCache_expire = Long.valueOf(map.get("inerCache_expire"));
        inerCache_expire = (_inerCache_expire != null && _inerCache_expire >= 0) ? _inerCache_expire : inerCache_expire;

        String _zipPassword = map.get("zipPassword");
        zipPassword = (_zipPassword != null && _zipPassword !="")?_zipPassword:zipPassword;

        String _data_daytips = map.get("data_daytips");
        data_daytips = (_data_daytips != null && _data_daytips !="")?_data_daytips:data_daytips;

        String _data_accountZip = map.get("data_accountZip");
        data_accountZip = (_data_accountZip != null && _data_accountZip !="")?_data_accountZip:data_accountZip;

        String _data_accountFileName = map.get("data_accountFileName");
        data_accountFileName = (_data_accountFileName != null && _data_accountFileName !="")?_data_accountFileName:data_accountFileName;

        String _encoding = map.get("encoding");
        encoding = (_encoding != null && _encoding !="")?_encoding:encoding;

        String _date_pattern = map.get("date_pattern");
        date_pattern = (_date_pattern != null && _date_pattern !="")?_date_pattern:date_pattern;

        String _time_pattern = map.get("time_pattern");
        time_pattern = (_time_pattern != null && _time_pattern !="")?_time_pattern:time_pattern;

        String _secret_key = map.get("secret_key");
        secret_key = (_secret_key != null && _secret_key !="")?_secret_key:secret_key;
    }

    private static String getConfigPath() {
        String configFilePath = Config.class.getClassLoader().getResource("config.properties").getPath().substring(1);
        // 判断系统 linux，windows
        if ("\\".equals(File.separator)) {
            configFilePath = configFilePath.replace("%20", " ");
        } else if ("/".equals(File.separator)) {
            configFilePath = "/" + configFilePath.replace("%20", " ");
        }
        return configFilePath;
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Map<String, String> map = PropertiesUtil.getAllProperty();
        if (map == null || map.isEmpty()) {
            return;
        }
        setFields(map);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }

    /**
     * 读取properties配置文件.
     * User: luowei
     * Date: 13-2-5
     * Time: 下午2:39
     * To change this template use File | Settings | File Templates.
     */
    public abstract static class PropertiesUtil {

        static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

        private static Properties props;
        private static String fileName = properties_fileName;

        private static void readProperties() {
            FileInputStream fis = null;
            try {
                if (props == null) {
                    props = new Properties();
                }
                fis = new FileInputStream(fileName);
                InputStreamReader is = new InputStreamReader(fis,"UTF-8");
                props.load(is);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
        }

        /**
         * 获取某个属性
         */
        public static String getProperty(String key) {
            readProperties();
            return props.getProperty(key);
        }

        /**
         * 获取所有属性，返回一个map,不常用
         * 可以试试props.putAll(t)
         */
        public static Map<String, String> getAllProperty() {
            Map<String, String> map = new HashMap<String, String>();
            readProperties();
            Enumeration enu = props.propertyNames();
            while (enu.hasMoreElements()) {
                String key = (String) enu.nextElement();
                String value = props.getProperty(key);
                map.put(key, (value!=null?value.trim():"") );
            }
            return map;
        }

        /**
         * 在控制台上打印出所有属性，调试时用。
         */
        public static void printProperties() {
            props.list(System.out);
        }

        /**
         * 写入properties信息
         */
        public static Boolean writeProperties(String key, String value) {
            OutputStream fos = null;
            try {
                fos = new FileOutputStream(fileName);
                props.setProperty(key, value);
                // 将此 Properties 表中的属性列表（键和元素对）写入输出流
                props.store(fos, "『comments』Update key：" + key);
                return true;
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                return false;
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
        }
        //    public static void main(String[] args) {
        //        PropertiesUtil util=new PropertiesUtil("config.properties");
        //        System.out.println("ip=" + util.getProperty("ip"));
        //        util.writeProperties("key", "value0");
        //    }
    }
}
