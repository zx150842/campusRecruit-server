package com.bupt.pcncad.domain;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-8-24
 * Time: 上午1:05
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "CAREER_TALK")
@JsonIgnoreProperties({"company"})
public class CareerTalk implements Serializable {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "identity")
    @GeneratedValue(generator = "idGenerator")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;
    @Column(name = "school_name")
    private String schoolName;
    @Column(name = "place")
    private String place;
    @Column(name = "career_talk_date")
    private String date;
    @Column(name = "clicks", columnDefinition = "int default 0")
    private int clicks;
    @Column(name = "joins", columnDefinition = "int default 0")
    private int joins;
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "topic_id", columnDefinition = "int default 0")
    private int topicID;
    @Column(name = "replies", columnDefinition = "int default 0")
    private int replies;
    @Column(name = "career_time")
    private String time;
    @Column(name = "input_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inputTime;
    @Column(name = "url")
    private String url;
    @Column(name = "source_from")
    private String sourceFrom;
    @Column(name = "delete_flag", columnDefinition = "int default 0")
    private int deleteFlag;           //1删除 2录入公司错误待修改
    @Column(name = "title")
    private String title;
    @Column(name = "org_id",columnDefinition = "int default 0")
    private int orgId;
    @Column(name = "db", columnDefinition = "int default 0")
    private int db;

    public int getId() {
        return id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public int getJoins() {
        return joins;
    }

    public void setJoins(int joins) {
        this.joins = joins;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getTopicID() {
        return topicID;
    }

    public void setTopicID(int topicID) {
        this.topicID = topicID;
    }

    public int getReplies() {
        return replies;
    }

    public void setReplies(int replies) {
        this.replies = replies;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Date getInputTime() {
        return inputTime;
    }

    public void setInputTime(Date inputTime) {
        this.inputTime = inputTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSourceFrom() {
        return sourceFrom;
    }

    public void setSourceFrom(String sourceFrom) {
        this.sourceFrom = sourceFrom;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public int getDb() {
        return db;
    }

    public void setDb(int db) {
        this.db = db;
    }
}
