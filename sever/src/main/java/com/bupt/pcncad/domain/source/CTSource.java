package com.bupt.pcncad.domain.source;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-9-11
 * Time: 下午10:33
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "dajiexj")
public class CTSource implements Serializable {
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "identity")
    private int id;

    @Column(name = "date")
    private String date;
    @Column(name = "local")
    private String local;
    @Column(name = "school")
    private String school;
    @Column(name = "time")
    private String time;
    @Column(name = "title")
    private String title;
    @Column(name = "url")
    private String url;
    @Column(name = "insert_flag",columnDefinition = "int default 0")
    private int insertFlag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getInsertFlag() {
        return insertFlag;
    }

    public void setInsertFlag(int insertFlag) {
        this.insertFlag = insertFlag;
    }
}
