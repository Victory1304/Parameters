package com.parameters.prts.Model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "method")
public class MethodEntity extends BaseEntity {

    @Column(name = "name_russian")
    private String nameRu;

    @Column(name = "name_foreign")
    private String nameForeign;

    @Column(name = "conditions")
    private String conditions;

    @Column(name = "restrictions")
    private String restrictions;

    @Column(name = "normal_value")
    private Double normalValue;

    @Column(name = "borderline_value")
    @Length(max = 25)
    private String borderlineValue;

    @Column(name = "info")
    private String info;

    @ManyToOne
    @JoinColumn(name = "id_source")
    private SourceEntity source;

    @OneToMany(mappedBy = "method")
    private final Set<MethodEquipmentEntity> methodEquipments = new HashSet<>();

    @OneToMany(mappedBy = "method")
    private final Set<BasicMethodEntity> basicMethod = new HashSet<>();

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public String getNameForeign() {
        return nameForeign;
    }

    public void setNameForeign(String nameForeign) {
        this.nameForeign = nameForeign;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
    }

    public Double getNormalValue() {
        return normalValue;
    }

    public void setNormalValue(Double normalValue) {
        this.normalValue = normalValue;
    }

    public String getBorderlineValue() {
        return borderlineValue;
    }

    public void setBorderlineValue(String borderlineValue) {
        this.borderlineValue = borderlineValue;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public SourceEntity getSource() {
        return source;
    }

    public void setSource(SourceEntity source) {
        this.source = source;
    }

    public Set<MethodEquipmentEntity> getMethodEquipments() {
        return methodEquipments;
    }

    public Set<BasicMethodEntity> getBasicMethod() {
        return basicMethod;
    }

    @Override
    public String toString() {
        return "Метод ("
                + "название : " + getNameRu()
                + ')';
    }
}
