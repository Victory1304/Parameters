package com.parameters.prts.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "vtr_prts")
public class SecondaryEntity extends BaseEntity {

    @Column(name = "prim")
    private String remark;

//    private Blob formula;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Вторичный ("
                + "id : " + id
                + "формула : "
                + "?" + ')';
    }
}
