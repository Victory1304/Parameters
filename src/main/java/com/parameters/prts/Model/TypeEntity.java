package com.parameters.prts.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "type_prts")
public class TypeEntity extends BaseEntity {
    @Column(name = "name_tp")
    private String nameTp;

    @OneToMany(mappedBy = "type")
    private final Set<GroupEntity> group = new HashSet<>();

    public String getNameTp() {
        return nameTp;
    }

    public void setNameTp(String nameTp) {
        this.nameTp = nameTp;
    }

    public Set<GroupEntity> getGroup() {
        return group;
    }

    @Override
    public String toString() {
        return "Тип (" +
                "название : " + nameTp +
                ')';
    }
}
