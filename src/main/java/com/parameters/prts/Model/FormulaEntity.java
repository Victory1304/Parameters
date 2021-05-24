package com.parameters.prts.Model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "formula")
public class FormulaEntity extends BaseEntity {

    @Column(name = "calculation")
    private String calculation;

    @Column(name = "normal_value")
    private Double normalValue;

    @Column(name = "borderline_value")
    @Length(max = 25)
    private String borderlineValue;

    @OneToMany(mappedBy = "formula")
    private final Set<BasicFormulaEntity> basicFormulas = new HashSet<>();

    public String getCalculation() {
        return calculation;
    }

    public void setCalculation(String calculation) {
        this.calculation = calculation;
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

    public Set<BasicFormulaEntity> getBasicFormulas() {
        return basicFormulas;
    }

    @Override
    public String toString() {
        return "Формула (" +
                "значение : " + normalValue +
                ')';
    }
}
