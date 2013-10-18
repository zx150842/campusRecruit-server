package com.bupt.pcncad.domain;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-8-23
 * Time: 下午11:56
 * To change this template use File | Settings | File Templates.
 */
//职位
@Entity
@Table(name = "JOB")
@JsonIgnoreProperties("company")
public class Job implements Serializable {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "identity")
    @GeneratedValue(generator = "idGenerator")
    private int id;

    @Column(name = "job_name")
    private String position;
    @Column(name = "industry")
    private String industry;
    @Column(name = "description")
    private String description;
    @Column(name = "contact")
    private String contact;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;
    @Column(name = "place")
    private String place;
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "joins", columnDefinition = "int default 0")
    private int joins;
    @Column(name = "created_time")
    private String createdTime;
    @Column(name = "replies", columnDefinition = "int default 0")
    private int replies;
    @Column(name = "topic_id", columnDefinition = "int default 0")
    private int topicID;
    @Column(name = "input_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inputTime;
    @Column(name = "company_type", columnDefinition = "int default 0")
    private int type;
    @Column(name = "state")
    private String state;
    @Column(name = "state_time")
    private String statetime;
    @Column(name = "clicks", columnDefinition = "int default 0")
    private int clicks;
    @Column(name = "source_from")
    private String sourceFrom;
    @Column(name = "url")
    private String url;
    @Column(name = "table_form")
    private String form;
    @Column(name = "img_url")
    private String imgUrl;
    @Column(name = "delete_flag", columnDefinition = "int default 0")
    private int deleteFlag;
    @Column(name = "title")
    private String title;
    @Column(name = "org_id",columnDefinition = "int default 0")
    private int orgId;
    @Column(name = "db", columnDefinition = "int default 0")
    private int db;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getJoins() {
        return joins;
    }

    public void setJoins(int joins) {
        this.joins = joins;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public int getReplies() {
        return replies;
    }

    public void setReplies(int replies) {
        this.replies = replies;
    }

    public int getTopicID() {
        return topicID;
    }

    public void setTopicID(int topicID) {
        this.topicID = topicID;
    }

    public Date getInputTime() {
        return inputTime;
    }

    public void setInputTime(Date inputTime) {
        this.inputTime = inputTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public String getSourceFrom() {
        return sourceFrom;
    }

    public void setSourceFrom(String sourceFrom) {
        this.sourceFrom = sourceFrom;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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
