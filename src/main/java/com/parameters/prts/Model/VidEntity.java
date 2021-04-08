package com.parameters.prts.Model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "vid_prts")
public class VidEntity extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "prts_id")
    private ParameterEntity parameter;

    @OneToOne
    @JoinColumn(name = "prv_prts_id")
    private PrimaryEntity primary;

    @OneToOne
    @JoinColumn(name = "vtr_prts_id")
    private SecondaryEntity secondary;


    public ParameterEntity getParameter() {
        return parameter;
    }

    public void setParameter(ParameterEntity parameter) {
        this.parameter = parameter;
    }

    public PrimaryEntity getPrimary() {
        return primary;
    }

    public void setPrimary(PrimaryEntity primary) {
        this.primary = primary;
    }

    public SecondaryEntity getSecondary() {
        return secondary;
    }

    public void setSecondary(SecondaryEntity secondary) {
        this.secondary = secondary;
    }

    @Override
    public String toString() {
        return "Вид (" +
                "параметр : " + parameter.getNameFP() +
                ')';
    }
}
