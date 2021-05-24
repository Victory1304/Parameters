package com.parameters.prts.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "equipment")
public class EquipmentEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "equipment")
    private final Set<MethodEquipmentEntity> methodEquipments = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<MethodEquipmentEntity> getMethodEquipments() {
        return methodEquipments;
    }

    @Override
    public String toString() {
        return "Оборудование (" +
                "название : " + name +
                ')';
    }
}
