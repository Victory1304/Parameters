package com.parameters.prts.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "param_group")
public class GroupEntity extends BaseEntity {
    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "group")
    private final Set<ParameterEntity> parameters = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "type_prts_id")
    private TypeEntity type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<ParameterEntity> getParameters() {
        return parameters;
    }

    public TypeEntity getType() {
        return type;
    }

    public void setType(TypeEntity type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Группа (" +
                "название : " + title +
                ')';
    }
}
