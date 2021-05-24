package com.parameters.prts.Model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "unite_method_equipment")
public class MethodEquipmentEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "id_method")
    private MethodEntity method;

    @ManyToOne
    @JoinColumn(name = "id_equipment")
    private EquipmentEntity equipment;

    public MethodEntity getMethod() {
        return method;
    }

    public void setMethod(MethodEntity method) {
        this.method = method;
    }

    public EquipmentEntity getEquipment() {
        return equipment;
    }

    public void setEquipment(EquipmentEntity equipment) {
        this.equipment = equipment;
    }

    @Override
    public String toString() {
        return "Оборудование методов ("
                + "индикатор : " + method.getNameRu()
                + " формула : " + equipment.getName()
                + ')';
    }
}
