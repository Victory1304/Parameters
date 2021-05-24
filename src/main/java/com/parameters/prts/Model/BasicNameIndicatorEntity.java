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
@Table(name = "basic_name_indicator")
public class BasicNameIndicatorEntity extends BaseEntity {

    @Column(name = "latin_name")
    private String latinName;

    @Column(name = "unit_measure")
    @Length(max = 100)
    private String unitMeasure;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_system")
    private SystemEntity system;

    @ManyToOne
    @JoinColumn(name = "id_groupsystem")
    private GroupSystemEntity groupSystem;

    @ManyToOne
    @JoinColumn(name = "id_type")
    private TypeEntity type;

    @OneToMany(mappedBy = "indicator")
    private final Set<AdditionalNameEntity> additionalNames = new HashSet<>();

    @OneToMany(mappedBy = "basicNameIndicator")
    private final Set<BasicFormulaEntity> basicFormulas = new HashSet<>();

    @OneToMany(mappedBy = "basicNameIndicator")
    private final Set<BasicFormulaEntity> basicMethods = new HashSet<>();

    public String getLatinName() {
        return latinName;
    }

    public void setLatinName(String latinName) {
        this.latinName = latinName;
    }

    public String getUnitMeasure() {
        return unitMeasure;
    }

    public void setUnitMeasure(String unitMeasure) {
        this.unitMeasure = unitMeasure;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SystemEntity getSystem() {
        return system;
    }

    public void setSystem(SystemEntity system) {
        this.system = system;
    }

    public GroupSystemEntity getGroupSystem() {
        return groupSystem;
    }

    public void setGroupSystem(GroupSystemEntity groupSystem) {
        this.groupSystem = groupSystem;
    }

    public TypeEntity getType() {
        return type;
    }

    public void setType(TypeEntity type) {
        this.type = type;
    }

    public Set<AdditionalNameEntity> getAdditionalNames() {
        return additionalNames;
    }

    public Set<BasicFormulaEntity> getBasicFormulas() {
        return basicFormulas;
    }

    public Set<BasicFormulaEntity> getBasicMethods() {
        return basicMethods;
    }

    @Override
    public String toString() {
        return "Индикатор ("
                + "название : " + getLatinName()
                + ')';
    }
}
