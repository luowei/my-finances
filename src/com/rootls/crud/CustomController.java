package com.rootls.crud;

import com.rootls.common.BaseController;
import com.rootls.common.Config;
import com.rootls.crud.model.Menu;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.rootls.common.Config.ctx;
import static com.rootls.common.InerCache.getMenuMap;

/**
 * Created by luowei on 2014/4/13.
 */
@Controller
public class CustomController extends BaseController {

    @Resource
    BeanRepository beanRepository;

    @ResponseBody
    @RequestMapping("/{type}/menuTree")
    public Collection<Menu> menuJsTree(HttpServletRequest request, @PathVariable String type) {
        ctx = request.getServletContext().getContextPath();
        Collection<Menu> menus = getMenuMap().values();
        for (Menu menu : menus) {
            String menuUrl = menu.getMenuUrl() != null && !menu.getMenuUrl().contains(ctx) ? ctx + menu.getMenuUrl() : menu.getMenuUrl();
            menu.setMenuUrl(menuUrl);
        }
        return menus;
    }

    @ResponseBody
    @RequestMapping("/{type}/getMenu")
    public String getBean(@PathVariable String type, Integer id) throws Exception {

        if (id != null) {
            Object bean = beanRepository.getById(type, id);
            String jsonBean = "{}";
            if (bean instanceof Menu) {
                Menu menu = (Menu) bean;
                jsonBean = "{\"id\":" + menu.getId() + ",\"parentId\":" + menu.getParentId() + ",\"menuName\":\"" + menu.getMenuName() + "\"," +
                        "\"menuUrl\":\"" + menu.getMenuUrl() + "\",\"describtion\":\"" + menu.getDescribtion() + "\"}";
            }
            return "{\"code\":1,\"msg\":\"ok\",\"data\":" + jsonBean + "}";
        } else {
            return "{\"code\":0,\"msg\":\"传入参数有误\"}";
        }
    }


    @JsonPropertyOrder({"id", "pId", "name", "url"})
    class Node {
        Integer id;
        Integer pId;
        String menuName;
        String menuUrl;

        Node(Integer id, String menuName) {
            this.id = id;
            this.menuName = menuName;
        }

        Node(Integer id, Integer pId, String menuName, String menuUrl) {
            this.id = id;
            this.pId = pId;
            this.menuName = menuName;
            this.menuUrl = menuUrl;
        }

        public Integer getId() {
            return id;
        }

        public Integer getpId() {
            return pId;
        }

        public void setpId(Integer pId) {
            this.pId = pId;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        @JsonProperty("name")
        public String getMenuName() {
            return menuName;
        }

        public void setMenuName(String menuName) {
            this.menuName = menuName;
        }

        @JsonProperty("url")
        public String getMenuUrl() {
            return menuUrl;
        }

        public void setMenuUrl(String menuUrl) {
            this.menuUrl = menuUrl;
        }

        public String getIconSkin() {
            return null;
        }

        public String getTarget() {
            return "_self";
        }


    }

}
