package com.parameters.prts.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "group_systems")
public class GroupSystemEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "groupSystem")
    private final Set<BasicNameIndicatorEntity> indicators = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<BasicNameIndicatorEntity> getIndicators() {
        return indicators;
    }

    @Override
    public String toString() {
        return "Группа (" +
                "название : " + name +
                ')';
    }
}
