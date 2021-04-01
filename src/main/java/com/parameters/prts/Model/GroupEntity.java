package com.parameters.prts.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "param_group")
public class GroupEntity extends BaseEntity {
    @Column(name = "title")
    private String title;

//    private ParameterEntity parameter;

//    private TypeEntity type;


    public GroupEntity() {
    }

    public GroupEntity(GroupEntity group) {
        this.title = group.getTitle();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
