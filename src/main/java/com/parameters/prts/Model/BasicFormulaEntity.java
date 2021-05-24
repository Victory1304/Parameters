package com.parameters.prts.Model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "unite_basic_formula")
public class BasicFormulaEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "id_basicname")
    private BasicNameIndicatorEntity basicNameIndicator;

    @ManyToOne
    @JoinColumn(name = "id_formula")
    private FormulaEntity formula;

    public BasicNameIndicatorEntity getBasicNameIndicator() {
        return basicNameIndicator;
    }

    public void setBasicNameIndicator(BasicNameIndicatorEntity basicNameIndicator) {
        this.basicNameIndicator = basicNameIndicator;
    }

    public FormulaEntity getFormula() {
        return formula;
    }

    public void setFormula(FormulaEntity formula) {
        this.formula = formula;
    }

    @Override
    public String toString() {
        return "Индикатор формулы ("
                + "индикатор : " + basicNameIndicator.getLatinName()
                + " формула : " + formula.getNormalValue()
                + ')';
    }
}
