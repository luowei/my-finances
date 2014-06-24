package com.rootls.crud;

import com.rootls.common.BaseController;
import com.rootls.common.Page;
import com.rootls.helper.ReadData;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.WebRequestDataBinder;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.rootls.common.Config.secret_key;
import static java.text.MessageFormat.format;

/**
 * Created by luowei on 2014/4/12.
 */
@Controller
public class BeanController extends BaseController {

    @Autowired
    ReadData readData;

    @Autowired
    BeanRepository beanRepository;

    static String listUrl = "/{0}/list";

    @RequestMapping("/{type}/list")
    public String list(/*HttpServletRequest*/ WebRequest request, Model model, Page page,
                       @PathVariable String type) throws Exception {
        //约定：传入的type值必须与对应数据表及类首字母小写的名称相同
//        Object entityObj = beanRepository.getEntityObj(type);
//        new WebRequestDataBinder(entityObj).bind(request);

        Object entityObj = binderEntity(request, type);

//        WebRequestDataBinder binder = new WebRequestDataBinder(entityObj);
//        binder.bind(request);

//        ServletRequestDataBinder binder = new ServletRequestDataBinder(entityObj);
//        binder.bind(request);

//        BindingResult result = binder.getBindingResult();
//        validator.validate(target, result);

        model.addAttribute("entity", entityObj);

        Page pg = beanRepository.page(type, entityObj, page);

        addPageInfo(model, pg, format(listUrl, type));
        return format(listUrl, type);
    }

    @ResponseBody
    @RequestMapping("/{type}/add")
    public String add(WebRequest request, @PathVariable String type) throws Exception {
        Object entityObj = binderEntity(request, type);

        int sum = 0;
        if (!beanRepository.exsit(type, entityObj)) {
            sum += beanRepository.add(type, entityObj);
        } else {
            return "{\"code\":0,\"msg\":\"存在重复记录,无法添加\"}";
        }

        return "{\"code\":1,\"msg\":\"" + sum + "条记录添加成功\",\"num\":" + sum + "}";
    }

    @ResponseBody
    @RequestMapping("/{type}/update")
    public String update(WebRequest request, @PathVariable String type) throws Exception {
        Object entityObj = binderEntity(request, type);

        if (beanRepository.exsitById(type, entityObj)) {
            int sum = beanRepository.update(type, entityObj);
            return "{\"code\":1,\"msg\":\"" + sum + "条记录更新成功\",\"num\":" + sum + "}";
        } else {
            return "{\"code\":0,\"msg\":\"不存在此记录,无法更新\"}";
        }
    }

    @ResponseBody
    @RequestMapping("/{type}/del")
    public String del(@PathVariable String type, Integer id) throws Exception {

        if (id != null) {
            int sum = beanRepository.delById(type, id);
            return "{\"code\":1,\"msg\":\"" + sum + "条记录删除成功\",\"num\":" + sum + "}";
        } else {
            return "{code:0,msg:\"传入参数有误\"}";
        }
    }

    @ResponseBody
    @RequestMapping("/{type}/getBean")
    public String getBean(@PathVariable String type, Integer id) throws Exception {

        if (id != null) {
            Object bean = beanRepository.getById(type, id);
            String jsonBean = new ObjectMapper().writeValueAsString(bean);
            return "{\"code\":1,\"msg\":\"ok\",\"data\":" + jsonBean + "}";
        } else {
            return "{\"code\":0,\"msg\":\"传入参数有误\"}";
        }
    }

    @ResponseBody
    @RequestMapping("/{type}/reloadBeans")
    public String reloadAccounts(@PathVariable String type) throws Exception {

        /*Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();*/
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        int num = beanRepository.delAll(type);

        List list = readData.readBeansFromZipFile(type, secret_key);
        int sum = 0;
        for (Object bean : list) {
            if (!beanRepository.exsit(type, bean)) {
                sum += beanRepository.add(type, bean);
            }
        }
        return "{\"code\":1,\"msg\":\"" + sum + "条记录添加成功\",\"num\":" + sum + "}";
    }



    private Object binderEntity(WebRequest request, String type) {
        Object entityObj = beanRepository.getEntityObj(type);
        WebRequestDataBinder binder = new WebRequestDataBinder(entityObj);
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            public void setAsText(String value) {
                try {
                    setValue(new SimpleDateFormat("yyyy-MM-dd").parse(value));
                } catch (ParseException e) {
                    setValue(null);
                }
            }

            public String getAsText() {
                return new SimpleDateFormat("yyyy-MM-dd").format((Date) getValue());
            }

        });
        binder.bind(request);
        return entityObj;
    }
}
