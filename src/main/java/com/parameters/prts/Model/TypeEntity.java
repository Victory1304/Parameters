package com.parameters.prts.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "type")
public class TypeEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "type")
    private final Set<BasicNameIndicatorEntity> indicators = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String nameTp) {
        this.name = nameTp;
    }

    @Override
    public String toString() {
        return "Тип (" +
                "название : " + name +
                ')';
    }
}
