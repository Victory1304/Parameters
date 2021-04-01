package com.parameters.prts.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "prv_prt")
public class PrimaryEntity extends BaseEntity {
    @Column(name = "metod_izmrn")
    private String measurementMethod;

    public PrimaryEntity() {
    }

    public PrimaryEntity(PrimaryEntity primary) {
        this.measurementMethod = primary.getMeasurementMethod();
    }

    public String getMeasurementMethod() {
        return measurementMethod;
    }

    public void setMeasurementMethod(String measurementMethod) {
        this.measurementMethod = measurementMethod;
    }
}
