package com.bupt.pcncad.domain.source;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-10-18
 * Time: 上午10:43
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "dajieCompany")
public class DajieCompany implements Serializable {
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "identity")
    private int id;

    @Column(name = "cAddress")
    private String address;
    @Column(name = "cDetils")
    private String details;
    @Column(name = "cLocal")
    private String local;
    @Column(name = "cName")
    private String name;
    @Column(name = "cNewIndustry")
    private String newIndustry;
    @Column(name = "cOldIndustry")
    private String oldIndustry;
    @Column(name = "cProperty")
    private String type;
    @Column(name = "cScale")
    private String scale;
    @Column(name = "cUrl")
    private String url;
    @Column(name = "insert_flag", columnDefinition = "int default 0")
    private int insertFlag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNewIndustry() {
        return newIndustry;
    }

    public void setNewIndustry(String newIndustry) {
        this.newIndustry = newIndustry;
    }

    public String getOldIndustry() {
        return oldIndustry;
    }

    public void setOldIndustry(String oldIndustry) {
        this.oldIndustry = oldIndustry;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
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
}
