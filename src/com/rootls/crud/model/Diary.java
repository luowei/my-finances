package com.rootls.crud.model;

import com.rootls.crud.AddQuery;
import com.rootls.crud.DB;
import org.springframework.format.annotation.DateTimeFormat;

import javax.print.attribute.standard.MediaSize;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by luowei on 2014/4/30.
 */
@DB
public class Diary implements Serializable {
    Integer id;
    @AddQuery
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE,pattern = "yyyy-MM-dd")
    Date diaryDate;
    @AddQuery
    String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDiaryDate() {
        return diaryDate;
    }

    public void setDiaryDate(Date diaryDate) {
        this.diaryDate = diaryDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
