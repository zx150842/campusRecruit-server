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
 * Time: 下午5:36
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "USER")
@JsonIgnoreProperties({"preference","major","bbsSectionSet","bbsReplySet"})
public class User implements Serializable {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "user_id")
    private String userId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_password")
    private String userPassword;
    @Column(name = "user_email")
    private String email;
    @Column(name = "user_gender", columnDefinition = "int default 0")
    private int gender;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "school_name")
    private String schoolName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "major_id")
    private Major major;
    @OneToOne
    @JoinColumn(name = "preference_id")
    private Preference preference;
    @Column(name = "introduction")
    private String introduction;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_BBS_SECTION", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "section_id"))
    private Set<BBSSection> bbsSectionSet = new HashSet<BBSSection>();
    @Column(name = "last_read_career_talk")
    private String usrLastReadTalk;
//    @Column(name = "oldest_read_career_talk")
//    private Date oldReadTalk;
    @Column(name = "last_read_campus_recruit")
    private String usrLastReadRecruit;
//    @Column(name = "oldest_read_campus_recruit")
//    private Date oldReadRecruit;
    @Column(name = "last_read_topic", columnDefinition = "int default 0")
    private int usrLastReadTopic;
    @Column(name = "input_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inputTime;
    @Column(name = "img_type")
    private String imgType;
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "user")
    private Set<BBSReply> bbsReplySet = new HashSet<BBSReply>();
    @Column(name = "last_read_reply", columnDefinition = "int default 0")
    private int usrLastReadReply;
    @Column(name = "last_read_letter", columnDefinition = "int default 0")
    private int usrLastReadLetter;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public Preference getPreference() {
        return preference;
    }

    public void setPreference(Preference preference) {
        this.preference = preference;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Set<BBSSection> getBbsSectionSet() {
        return bbsSectionSet;
    }

    public void setBbsSectionSet(Set<BBSSection> bbsSectionSet) {
        this.bbsSectionSet = bbsSectionSet;
    }

    public String getUsrLastReadTalk() {
        return usrLastReadTalk;
    }

    public void setUsrLastReadTalk(String usrLastReadTalk) {
        this.usrLastReadTalk = usrLastReadTalk;
    }

    public String getUsrLastReadRecruit() {
        return usrLastReadRecruit;
    }

    public void setUsrLastReadRecruit(String usrLastReadRecruit) {
        this.usrLastReadRecruit = usrLastReadRecruit;
    }

    public Date getInputTime() {
        return inputTime;
    }

    public void setInputTime(Date inputTime) {
        this.inputTime = inputTime;
    }

    public int getUsrLastReadTopic() {
        return usrLastReadTopic;
    }

    public void setUsrLastReadTopic(int usrLastReadTopic) {
        this.usrLastReadTopic = usrLastReadTopic;
    }

    public String getImgType() {
        return imgType;
    }

    public void setImgType(String imgType) {
        this.imgType = imgType;
    }

    public Set<BBSReply> getBbsReplySet() {
        return bbsReplySet;
    }

    public void setBbsReplySet(Set<BBSReply> bbsReplySet) {
        this.bbsReplySet = bbsReplySet;
    }

    public int getUsrLastReadReply() {
        return usrLastReadReply;
    }

    public void setUsrLastReadReply(int usrLastReadReply) {
        this.usrLastReadReply = usrLastReadReply;
    }

    public int getUsrLastReadLetter() {
        return usrLastReadLetter;
    }

    public void setUsrLastReadLetter(int usrLastReadLetter) {
        this.usrLastReadLetter = usrLastReadLetter;
    }
}
