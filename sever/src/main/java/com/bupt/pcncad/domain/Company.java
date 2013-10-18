package com.bupt.pcncad.domain;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.compass.annotations.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-8-23
 * Time: 下午11:32
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Searchable
@Table(name = "COMPANY")
@JsonIgnoreProperties({"bbsSection"})
public class Company implements Serializable {
    @Id
    @SearchableId
    @GenericGenerator(name = "idGenerator", strategy = "identity")
    @GeneratedValue(generator = "idGenerator")
    private int id;

    @SearchableProperty(index=Index.ANALYZED ,store=Store.YES)
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "company_type", columnDefinition = "int default 0")
    private int type;                //0:国企 1:私企
    @Column(name = "company_scale")
    private String scale;
    @Column(name = "province")
    private String place;
    @Column(name = "introduction")
    private String introduction;
    @Column(name = "company_rating", columnDefinition = "int default 0")
    private int companyRating;
    @Column(name = "section_id", columnDefinition = "int default 0")
    private int sectionID;
    @Column(name = "industry")
    private String industry;
    @Column(name = "input_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inputTime;
    @Column(name = "state")
    private String state;
    @Column(name = "state_time")
    private String statetime;
    @Column(name = "famous_company", columnDefinition = "int default 0")
    private int famousCompany;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getCompanyRating() {
        return companyRating;
    }

    public void setCompanyRating(int companyRating) {
        this.companyRating = companyRating;
    }

    public int getSectionID() {
        return sectionID;
    }

    public void setSectionID(int sectionID) {
        this.sectionID = sectionID;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public Date getInputTime() {
        return inputTime;
    }

    public void setInputTime(Date inputTime) {
        this.inputTime = inputTime;
    }

    public String getStatetime() {
        return statetime;
    }

    public void setStatetime(String statetime) {
        this.statetime = statetime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getFamousCompany() {
        return famousCompany;
    }

    public void setFamousCompany(int famousCompany) {
        this.famousCompany = famousCompany;
    }
}
