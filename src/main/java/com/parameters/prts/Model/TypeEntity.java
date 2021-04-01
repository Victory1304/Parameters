package com.parameters.prts.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "type_prt")
public class TypeEntity extends BaseEntity {
    @Column(name = "name_tp")
    private String nameTp;

    public TypeEntity() {
    }

    public TypeEntity(TypeEntity type) {
        this.nameTp = type.getNameTp();
    }

    public String getNameTp() {
        return nameTp;
    }

    public void setNameTp(String nameTp) {
        this.nameTp = nameTp;
    }
}
