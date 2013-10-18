package com.bupt.pcncad.domain.source;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-10-15
 * Time: 上午11:10
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "byrXJ")
public class BYRCTSource implements Serializable {
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "identity")
    private int id;

    @Column(name = "company")
    private String companyName;
    @Column(name = "date")
    private String date;
    @Column(name = "local")
    private String local;
    @Column(name = "time")
    private String time;
    @Column(name = "title")
    private String title;
    @Column(name = "insert_flag",columnDefinition = "int default 0")
    private int insertFlag;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getInsertFlag() {
        return insertFlag;
    }

    public void setInsertFlag(int insertFlag) {
        this.insertFlag = insertFlag;
    }
}
