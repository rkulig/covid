package com.rkulig.covid.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class DayOfSimulation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer day;
    private Long pi;
    private Long pv;
    private Long pm;
    private Long pr;
    private Long pia; // liczba wszystkich zakazonych (aktywnych) danego dnia
    private Long pid; // liczba nowych zakazonych danego dnia (przyrost)
    private Long pmd; // liczba zmarlych danego dnia (przyrost)
    private Long prd; // liczba osob ktore wyzdrowialy danego dnia(przyrost)


    @ManyToOne
    @JsonBackReference
    private Simulation simulation;

  protected DayOfSimulation() {
    }

    public DayOfSimulation(Integer day, Long pi, Long pv, Long pm, Long pr, Long pia, Long pid, Long pmd, Long prd) {
        this.day = day;
        this.pi = pi;
        this.pv = pv;
        this.pm = pm;
        this.pr = pr;
        this.pia = pia;
        this.pid = pid;
        this.pmd = pmd;
        this.prd = prd;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Long getPi() {
        return pi;
    }

    public void setPi(Long pi) {
        this.pi = pi;
    }

    public Long getPv() {
        return pv;
    }

    public void setPv(Long pv) {
        this.pv = pv;
    }

    public Long getPm() {
        return pm;
    }

    public void setPm(Long pm) {
        this.pm = pm;
    }

    public Long getPr() {
        return pr;
    }

    public void setPr(Long pr) {
        this.pr = pr;
    }

    public Long getPia() {
        return pia;
    }

    public void setPia(Long pia) {
        this.pia = pia;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getPmd() {
        return pmd;
    }

    public void setPmd(Long pmd) {
        this.pmd = pmd;
    }

    public Long getPrd() {
        return prd;
    }

    public void setPrd(Long prd) {
        this.prd = prd;
    }

    public Simulation getSimulation() {
        return simulation;
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DayOfSimulation that = (DayOfSimulation) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (day != null ? !day.equals(that.day) : that.day != null) return false;
        if (pi != null ? !pi.equals(that.pi) : that.pi != null) return false;
        if (pv != null ? !pv.equals(that.pv) : that.pv != null) return false;
        if (pm != null ? !pm.equals(that.pm) : that.pm != null) return false;
        if (pr != null ? !pr.equals(that.pr) : that.pr != null) return false;
        if (pia != null ? !pia.equals(that.pia) : that.pia != null) return false;
        if (pid != null ? !pid.equals(that.pid) : that.pid != null) return false;
        if (pmd != null ? !pmd.equals(that.pmd) : that.pmd != null) return false;
        if (prd != null ? !prd.equals(that.prd) : that.prd != null) return false;
        return simulation != null ? simulation.equals(that.simulation) : that.simulation == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (day != null ? day.hashCode() : 0);
        result = 31 * result + (pi != null ? pi.hashCode() : 0);
        result = 31 * result + (pv != null ? pv.hashCode() : 0);
        result = 31 * result + (pm != null ? pm.hashCode() : 0);
        result = 31 * result + (pr != null ? pr.hashCode() : 0);
        result = 31 * result + (pia != null ? pia.hashCode() : 0);
        result = 31 * result + (pid != null ? pid.hashCode() : 0);
        result = 31 * result + (pmd != null ? pmd.hashCode() : 0);
        result = 31 * result + (prd != null ? prd.hashCode() : 0);
        result = 31 * result + (simulation != null ? simulation.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DayOfSimulation{" +
                "id=" + id +
                ", day=" + day +
                ", pi=" + pi +
                ", pv=" + pv +
                ", pm=" + pm +
                ", pr=" + pr +
                ", pia=" + pia +
                ", pid=" + pid +
                ", pmd=" + pmd +
                ", prd=" + prd +
                ", simulation=" + simulation +
                '}';
    }
}
