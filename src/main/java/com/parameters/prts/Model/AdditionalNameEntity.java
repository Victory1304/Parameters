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
@Table(name = "additional_name")
public class AdditionalNameEntity extends BaseEntity {

    @Column(name = "abbreviation")
    @Length(max = 25)
    private String abbreviation;

    @Column(name = "decoding_abbrev")
    private String decodingAbbreviation;

    @ManyToOne
    @JoinColumn(name = "id_language")
    private LanguageEntity language;

    @ManyToOne
    @JoinColumn(name = "id_basicname")
    private BasicNameIndicatorEntity indicator;

    @OneToMany(mappedBy = "additionalName")
    private final Set<LiteratureNamesEntity> literatureNames = new HashSet<>();

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getDecodingAbbreviation() {
        return decodingAbbreviation;
    }

    public void setDecodingAbbreviation(String decodingAbbreviation) {
        this.decodingAbbreviation = decodingAbbreviation;
    }

    public LanguageEntity getLanguage() {
        return language;
    }

    public void setLanguage(LanguageEntity language) {
        this.language = language;
    }

    public BasicNameIndicatorEntity getIndicator() {
        return indicator;
    }

    public void setIndicator(BasicNameIndicatorEntity indicator) {
        this.indicator = indicator;
    }

    public Set<LiteratureNamesEntity> getLiteratureNames() {
        return literatureNames;
    }

    @Override
    public String toString() {
        return "Дополнительное название ("
                + "аббериватура : " + abbreviation
                + ')';
    }
}
