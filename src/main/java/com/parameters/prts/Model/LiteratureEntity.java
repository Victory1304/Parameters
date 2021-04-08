package com.parameters.prts.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "literature")
public class LiteratureEntity extends BaseEntity {

    @Column(name = "site")
    private String site;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "prts_id")
    private ParameterEntity parameter;

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

    public ParameterEntity getParameter() {
        return parameter;
    }

    public void setParameter(ParameterEntity parameter) {
        this.parameter = parameter;
    }

    @Override
    public String toString() {
        return "Литература (" +
                "название : " + title +
                ')';
    }
}
