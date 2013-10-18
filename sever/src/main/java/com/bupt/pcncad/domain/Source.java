package com.bupt.pcncad.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-9-4
 * Time: 下午3:40
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "dajielist")
public class Source implements Serializable {
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "identity")
    private int pid;

    @Column(name = "type")
    private String category;
    @Column(name = "url")
    private String url;
    @Column(name = "title")
    private String title;
    @Column(name = "CompanyProperty")
    private String type;
    @Column(name = "city")
    private String city;
    @Column(name = "industry")
    private String industry;
    @Column(name = "releaseDate")
    private String releaseDate;
    @Column(name = "state")
    private String state;
    @Column(name = "statetime")
    private String statetime;
    @Column(name = "jobdetails")
    private String jobdetails;
    @Column(name = "form")
    private String form;
    @Column(name = "imageurl")
    private String imageurl;
    @Column(name = "isfresh",columnDefinition = "int default 0")  //0表示还没有录入数据库
    private int insertFlag;
    @Column(name = "isfame", columnDefinition = "int default 0")
    private int isfame;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatetime() {
        return statetime;
    }

    public void setStatetime(String statetime) {
        this.statetime = statetime;
    }

    public String getJobdetails() {
        return jobdetails;
    }

    public void setJobdetails(String jobdetails) {
        this.jobdetails = jobdetails;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public int getInsertFlag() {
        return insertFlag;
    }

    public void setInsertFlag(int insertFlag) {
        this.insertFlag = insertFlag;
    }

    public int getIsfame() {
        return isfame;
    }

    public void setIsfame(int isfame) {
        this.isfame = isfame;
    }
}
