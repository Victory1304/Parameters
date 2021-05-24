package com.parameters.prts.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "literature")
public class LiteratureEntity extends BaseEntity {

    @Column(name = "website")
    private String website;

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "literature")
    private final Set<LiteratureNamesEntity> literatureNames = new HashSet<>();

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String site) {
        this.website = site;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<LiteratureNamesEntity> getLiteratureNames() {
        return literatureNames;
    }

    @Override
    public String toString() {
        return "Литература (" +
                "название : " + title +
                ')';
    }
}
