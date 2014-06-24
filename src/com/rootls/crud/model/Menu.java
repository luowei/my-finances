package com.rootls.crud.model;

import com.rootls.common.Config;
import com.rootls.common.InerCache;
import com.rootls.crud.AddQuery;
import com.rootls.crud.DB;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

import java.io.Serializable;

import static com.rootls.common.Config.ctx;

/**
 * Created by luowei on 2014/4/12.
 */
@DB
@JsonPropertyOrder({"id","pId","name","url","target","chkDisabled"})
public class Menu implements Serializable {
    private Integer id;
    @AddQuery
    private String menuName;
    @AddQuery
    private String describtion;
    private String menuUrl;
    @AddQuery
    private Integer parentId;
    private Integer sort;

    @DB(false)
    private Boolean isParent;
    @DB(false)
    private String parent;



    public Menu() {
    }

    public Menu(Integer id, String menuName, String describtion, String menuUrl, Boolean isParent) {
        this.id = id;
        this.menuName = menuName;
        this.describtion = describtion;
        this.menuUrl = menuUrl;
        this.isParent = isParent;
    }

    public Integer getId() {
        return id;
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

    @JsonIgnore
    public String getDescribtion() {
        return describtion;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion;
    }

    @JsonProperty("url")
    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    @JsonIgnore
    public Boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(Boolean isParent) {
        this.isParent = isParent;
    }

    @JsonProperty("pId")
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @JsonIgnore
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @JsonIgnore
    public String getParent() {
        if(parentId!=null){
            Menu parentMenu = InerCache.getMenuMap().get(parentId);
            if(parentMenu!=null){
                parent = parentId+":"+parentMenu.getMenuName();
            }else {
                parent = parentId+"";
            }
        }
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    @JsonProperty
    public String getTarget(){
        return "_self";
    }

    @JsonProperty
    public boolean getChkDisabled(){
         if(isParent==null || !isParent){
             return true;
         }
        return false;
    }
}
