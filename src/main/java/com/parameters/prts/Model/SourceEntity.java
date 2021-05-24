package com.parameters.prts.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "source")
public class SourceEntity extends BaseEntity {

    @Column(name = "website")
    private String website;

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "source")
    private final Set<MethodEntity> methods = new HashSet<>();

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<MethodEntity> getMethods() {
        return methods;
    }

    @Override
    public String toString() {
        return "Источник (" +
                "название : " + title +
                ')';
    }
}
