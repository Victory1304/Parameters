package com.parameters.prts.Model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "unite_indicator_method")
public class BasicMethodEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "id_basicname")
    private BasicNameIndicatorEntity basicNameIndicator;

    @ManyToOne
    @JoinColumn(name = "id_method")
    private MethodEntity method;

    public BasicNameIndicatorEntity getBasicNameIndicator() {
        return basicNameIndicator;
    }

    public void setBasicNameIndicator(BasicNameIndicatorEntity basicNameIndicator) {
        this.basicNameIndicator = basicNameIndicator;
    }

    public MethodEntity getMethod() {
        return method;
    }

    public void setMethod(MethodEntity method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "Индикаторы методов ("
                + "индикатор : " + basicNameIndicator.getLatinName()
                + " метод : " + method.getNameRu()
                + ')';
    }
}
