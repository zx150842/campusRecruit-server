package com.bupt.pcncad.domain.source;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-9-15
 * Time: 下午5:55
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "qinghualist")
public class ShuiMuBBSSource implements Serializable {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "identity")
    @GeneratedValue(generator = "idGenerator")
    private int pid;

    @Column(name = "jobdetails")
    private String jobdetails;
    @Column(name = "releaseDate")
    private String releaseDate;
    @Column(name = "title")
    private String title;
    @Column(name = "url")
    private String url;
    @Column(name = "insert_flag",columnDefinition = "int default 0")  //0表示还没有录入数据库
    private int insertFlag;
    @Column(name = "city")
    private String province;
    @Column(name = "industry")
    private String industry;
    @Column(name = "CompanyProperty")
    private String type;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getJobdetails() {
        return jobdetails;
    }

    public void setJobdetails(String jobdetails) {
        this.jobdetails = jobdetails;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
