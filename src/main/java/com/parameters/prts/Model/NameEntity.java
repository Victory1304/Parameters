package com.parameters.prts.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "name_prts")
public class NameEntity extends BaseEntity {
    @Column(name = "name_altr")
    private String name;

    @Column(name = "abbr_altr")
    private String abbr;

    private Integer nvers;

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
}
