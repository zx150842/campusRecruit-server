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
 * Time: 下午4:41
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "BBS_REPLY")
@JsonIgnoreProperties({"user", "topic"})
public class BBSReply implements Serializable {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "identity")
    @GeneratedValue(generator = "idGenerator")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    private BBSTopic topic;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User user;
    @Column(name = "content")
    private String content;
    @Column(name = "ref_reply_id")
    private String refReplyIds;
    @Column(name = "last_modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModified;
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;
    @Column(name = "user_name")
    private String  userName;
    @Column(name = "input_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inputTime;
    @Column(name = "be_replied")
    private String beReply;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BBSTopic getTopic() {
        return topic;
    }

    public void setTopic(BBSTopic topic) {
        this.topic = topic;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRefReplyIds() {
        return refReplyIds;
    }

    public void setRefReplyIds(String refReplyIds) {
        this.refReplyIds = refReplyIds;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getInputTime() {
        return inputTime;
    }

    public void setInputTime(Date inputTime) {
        this.inputTime = inputTime;
    }

    public String getBeReply() {
        return beReply;
    }

    public void setBeReply(String beReply) {
        this.beReply = beReply;
    }
}
