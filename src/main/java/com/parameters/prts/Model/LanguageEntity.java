package com.parameters.prts.Model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "language")
public class LanguageEntity extends BaseEntity {

    @Column(name = "name")
    @Length(max = 100)
    private String name;

    @OneToMany(mappedBy = "language")
    private final Set<AdditionalNameEntity> additionalNames = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<AdditionalNameEntity> getAdditionalNames() {
        return additionalNames;
    }

    @Override
    public String toString() {
        return "Язык ("
                + "название : " + name
                + ')';
    }
}
