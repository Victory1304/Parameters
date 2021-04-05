package com.parameters.prts.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "literature")
public class LiteratureEntity extends BaseEntity {

    @Column(name = "site")
    private String site;

    @Column(name = "title")
    private String title;

//    @ManyToOne
//    @JoinColumn(name = "prts")
//    private ParameterEntity prts;

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //    public ParameterEntity getPrts() {
//        return prts;
//    }
//
//    public void setPrts(ParameterEntity prts) {
//        this.prts = prts;
//    }
}
