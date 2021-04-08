package com.parameters.prts.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "prts")
public class ParameterEntity extends BaseEntity {

    @Column(name = "vid")
    private String vid;

    @Column(name = "edinizmeren")
    private String edinIzmeren;

    @Column(name = "opisaniefp")
    private String opisanieFP;

    @Column(name = "oblastprimenen")
    private String oblastPrimenen;

    @Column(name = "namefp")
    private String nameFP;

    @Column(name = "refer")
    private String refer;

    @OneToMany(mappedBy = "parameter")
    private final Set<LiteratureEntity> literatures = new HashSet<>();

    @OneToMany(mappedBy = "parameter")
    private final Set<NameEntity> names = new HashSet<>();

    @OneToOne(mappedBy = "parameter")
    private VidEntity vidEntity;

    @OneToMany(mappedBy = "parameter")
    private final Set<GroupEntity> groups = new HashSet<>();

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getEdinIzmeren() {
        return edinIzmeren;
    }

    public void setEdinIzmeren(String edinIzmeren) {
        this.edinIzmeren = edinIzmeren;
    }

    public String getOpisanieFP() {
        return opisanieFP;
    }

    public void setOpisanieFP(String opisanieFP) {
        this.opisanieFP = opisanieFP;
    }

    public String getOblastPrimenen() {
        return oblastPrimenen;
    }

    public void setOblastPrimenen(String oblastPrimenen) {
        this.oblastPrimenen = oblastPrimenen;
    }

    public String getNameFP() {
        return nameFP;
    }

    public void setNameFP(String nameFP) {
        this.nameFP = nameFP;
    }

    public String getRefer() {
        return refer;
    }

    public void setRefer(String refer) {
        this.refer = refer;
    }

    public Set<LiteratureEntity> getLiteratures() {
        return literatures;
    }

    public Set<NameEntity> getNames() {
        return names;
    }

    public VidEntity getVidEntity() {
        return vidEntity;
    }

    public void setVidEntity(VidEntity vidEntity) {
        this.vidEntity = vidEntity;
    }

    public Set<GroupEntity> getGroups() {
        return groups;
    }

    @Override
    public String toString() {
        return "Параметр (" +
                "вид : " + vid +
                ", название : " + nameFP +
                ')';
    }
}
