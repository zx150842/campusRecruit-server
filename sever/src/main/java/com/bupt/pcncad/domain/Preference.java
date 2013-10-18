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
 * Time: 上午11:14
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "PREFERENCE")
@JsonIgnoreProperties("user")
public class Preference implements Serializable {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "identity")
    @GeneratedValue(generator = "idGenerator")
    private int id;

    @Column(name = "province")
    private String province;
    @Column(name = "company_type")
    private String companyType;        //国企1 私企2 外企3
    @Column(name = "industry")
    private String vocationSet;        //计算机1 通信2 电子3
    @Column(name = "notify_type")
    private String notifyType;         //宣讲会1 校招2 私信3 帖子回复4
    @Column(name = "source_from")
    private String sourceFrom;         //大街1 北邮人2 水木3 全部0
    @OneToOne(mappedBy = "preference")
    private User user;
    @Column(name = "input_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inputTime;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getVocationSet() {
        return vocationSet;
    }

    public void setVocationSet(String vocationSet) {
        this.vocationSet = vocationSet;
    }

    public String getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(String notifyType) {
        this.notifyType = notifyType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getInputTime() {
        return inputTime;
    }

    public void setInputTime(Date inputTime) {
        this.inputTime = inputTime;
    }

    public String getSourceFrom() {
        return sourceFrom;
    }

    public void setSourceFrom(String sourceFrom) {
        this.sourceFrom = sourceFrom;
    }
}
