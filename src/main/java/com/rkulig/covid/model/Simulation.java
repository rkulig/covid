package com.rkulig.covid.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class Simulation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //n - name of simulation
    private String n;
    //p - amount of population
    private Long p;
    //i - initial number of infected people
    private Long i;
    //r - indicator of how many people one person is infected with
    private Double r;
    //m - mortality indicator
    private Double m;
    //ti - number of days from the time of infection until the patient's recovery
    private Integer ti;
    //tm - number of days from the time of infection to the patient's death
    private Integer tm;
    //ts - Number of days for which the simulation is to be run
    private Integer ts;
    @OneToMany(mappedBy = "simulation")
    @JsonManagedReference
    private List<DayOfSimulation> daysOfSimulation;

    public Simulation() {
    }

    public Simulation(String n, Long p, Long i, Double r, Double m, Integer ti, Integer tm, Integer ts) {
        this.n = n;
        this.p = p;
        this.i = i;
        this.r = r;
        this.m = m;
        this.ti = ti;
        this.tm = tm;
        this.ts = ts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public Long getP() {
        return p;
    }

    public void setP(Long p) {
        this.p = p;
    }

    public Long getI() {
        return i;
    }

    public void setI(Long i) {
        this.i = i;
    }

    public Double getR() {
        return r;
    }

    public void setR(Double r) {
        this.r = r;
    }

    public Double getM() {
        return m;
    }

    public void setM(Double m) {
        this.m = m;
    }

    public Integer getTi() {
        return ti;
    }

    public void setTi(Integer ti) {
        this.ti = ti;
    }

    public Integer getTm() {
        return tm;
    }

    public void setTm(Integer tm) {
        this.tm = tm;
    }

    public Integer getTs() {
        return ts;
    }

    public void setTs(Integer ts) {
        this.ts = ts;
    }

    public List<DayOfSimulation> getDaysOfSimulation() {
        return daysOfSimulation;
    }

    public void setDaysOfSimulation(List<DayOfSimulation> daysOfSimulation) {
        this.daysOfSimulation = daysOfSimulation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Simulation that = (Simulation) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (n != null ? !n.equals(that.n) : that.n != null) return false;
        if (p != null ? !p.equals(that.p) : that.p != null) return false;
        if (i != null ? !i.equals(that.i) : that.i != null) return false;
        if (r != null ? !r.equals(that.r) : that.r != null) return false;
        if (m != null ? !m.equals(that.m) : that.m != null) return false;
        if (ti != null ? !ti.equals(that.ti) : that.ti != null) return false;
        if (tm != null ? !tm.equals(that.tm) : that.tm != null) return false;
        return ts != null ? ts.equals(that.ts) : that.ts == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (n != null ? n.hashCode() : 0);
        result = 31 * result + (p != null ? p.hashCode() : 0);
        result = 31 * result + (i != null ? i.hashCode() : 0);
        result = 31 * result + (r != null ? r.hashCode() : 0);
        result = 31 * result + (m != null ? m.hashCode() : 0);
        result = 31 * result + (ti != null ? ti.hashCode() : 0);
        result = 31 * result + (tm != null ? tm.hashCode() : 0);
        result = 31 * result + (ts != null ? ts.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Simulation{" +
                "id=" + id +
                ", n='" + n + '\'' +
                ", p=" + p +
                ", i=" + i +
                ", r=" + r +
                ", m=" + m +
                ", ti=" + ti +
                ", tm=" + tm +
                ", ts=" + ts +
                ", daysOfSimulation=" + daysOfSimulation +
                '}';
    }
}
