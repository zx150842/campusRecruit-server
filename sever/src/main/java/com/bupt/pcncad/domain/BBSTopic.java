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
 * Time: 下午5:25
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "BBS_TOPIC")
@JsonIgnoreProperties({"bbsSection", "user","replySet"})
public class BBSTopic implements Serializable {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "identity")
    @GeneratedValue(generator = "idGenerator")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id")
    private BBSSection bbsSection;
    @Column(name = "topic_category")
    private int category;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User user;
    @Column(name = "topic_title")
    private String title;
    @Column(name = "topic_body")
    private String body;
    @Column(name = "clicks",columnDefinition = "int default 0")
    private int clicks;
    @Column(name = "delete_flag",columnDefinition = "int default 0")
    private int deleteFlag;
    @Column(name = "created_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("createdTime asc")
    @JoinColumn(name = "topic_id")
    private Set<BBSReply> replySet = new HashSet<BBSReply>();
    @Column(name = "user_name")
    private String userName;
    @Column(name = "replies",columnDefinition = "int default 0")
    private int replies;
    @Column(name = "last_reply_time")
    private Date lastReplyTime;
    @Column(name = "input_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inputTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BBSSection getBbsSection() {
        return bbsSection;
    }

    public void setBbsSection(BBSSection bbsSection) {
        this.bbsSection = bbsSection;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Set<BBSReply> getReplySet() {
        return replySet;
    }

    public void setReplySet(Set<BBSReply> replySet) {
        this.replySet = replySet;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getReplies() {
        return replies;
    }

    public void setReplies(int replies) {
        this.replies = replies;
    }

    public Date getLastReplyTime() {
        return lastReplyTime;
    }

    public void setLastReplyTime(Date lastReplyTime) {
        this.lastReplyTime = lastReplyTime;
    }

    public Date getInputTime() {
        return inputTime;
    }

    public void setInputTime(Date inputTime) {
        this.inputTime = inputTime;
    }
}
