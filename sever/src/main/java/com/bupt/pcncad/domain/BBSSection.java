package com.bupt.pcncad.domain;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-8-23
 * Time: 下午5:20
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "BBS_SECTION")
@JsonIgnoreProperties({"user","users"})
public class BBSSection implements Serializable {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "identity")
    @GeneratedValue(generator = "idGenerator")
    private int id;

    @Column(name = "section_name")
    private String sectionName;
    @Column(name = "company_id", columnDefinition = "int default 0")
    private int companyID;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_id")
    private User user;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "bbsSectionSet")
    private Set<User>users = new HashSet<User>();
    @Column(name = "clicks",columnDefinition = "int default 0")
    private int clicks;
    @Column(name = "joins",columnDefinition = "int default 0")
    private int joins;
    @Column(name = "topics",columnDefinition = "int default 0")
    private int topics;
    @Column(name = "input_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inputTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
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

    public int getTopics() {
        return topics;
    }

    public void setTopics(int topics) {
        this.topics = topics;
    }

    public Date getInputTime() {
        return inputTime;
    }

    public void setInputTime(Date inputTime) {
        this.inputTime = inputTime;
    }
}
