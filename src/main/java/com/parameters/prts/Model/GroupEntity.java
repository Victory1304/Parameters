package com.parameters.prts.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "param_group")
public class GroupEntity extends BaseEntity {
    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "prts_id")
    private ParameterEntity parameter;

    @ManyToOne
    @JoinColumn(name = "type_prts_id")
    private TypeEntity type;

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
