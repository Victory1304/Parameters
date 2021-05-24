package com.parameters.prts.Model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "unite_literature_name")
public class LiteratureNamesEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "id_addname")
    private AdditionalNameEntity additionalName;

    @ManyToOne
    @JoinColumn(name = "id_literature")
    private LiteratureEntity literature;

    public AdditionalNameEntity getAdditionalName() {
        return additionalName;
    }

    public void setAdditionalName(AdditionalNameEntity additionalName) {
        this.additionalName = additionalName;
    }

    public LiteratureEntity getLiterature() {
        return literature;
    }

    public void setLiterature(LiteratureEntity literature) {
        this.literature = literature;
    }

    @Override
    public String toString() {
        return "Индикатор формулы ("
                + "индикатор : " + additionalName.getAbbreviation()
                + " формула : " + literature.getTitle()
                + ')';
    }
}
