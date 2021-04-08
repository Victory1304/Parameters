package com.parameters.prts.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "name_prts")
public class NameEntity extends BaseEntity {
    @Column(name = "name_altr")
    private String name;

    @Column(name = "abbr_altr")
    private String abbr;

    @Column(name = "nvers")
    private Integer nvers;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "prts_id")
    private ParameterEntity parameter;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public Integer getNvers() {
        return nvers;
    }

    public void setNvers(Integer nvers) {
        this.nvers = nvers;
    }

    public ParameterEntity getParameter() {
        return parameter;
    }

    public void setParameter(ParameterEntity parameter) {
        this.parameter = parameter;
    }

    @Override
    public String toString() {
        return "Название (" + "наименование : " + name + ')';
    }
}
