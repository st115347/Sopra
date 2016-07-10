package de.uni.hohenheim.sopra.projekt.model;

import javax.persistence.*;

/**
 * Created by Tabea on 07.07.16.
 */
@Entity
public class Tupel {

    @Id
    @GeneratedValue
    @Column(name="id")
    private Integer id;
    @ManyToOne
    private MCquestion q;
    @Column(name="a1")
    private Integer a1;
    @Column(name="a2")
    private Integer a2;
    @Column(name="a3")
    private Integer a3;
    @Column(name="a4")
    private Integer a4;
    @Column(name="qid")
    private Integer qid;


    public MCquestion getQ() {
        return q;
    }

    public void setQ(MCquestion q) {
        this.q = q;
    }

    public Integer getA1() {
        return a1;
    }

    public void setA1(Integer a) {
        this.a1 = a;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getA2() {
        return a2;
    }

    public void setA2(Integer a2) {
        this.a2 = a2;
    }

    public Integer getA3() {
        return a3;
    }

    public void setA3(Integer a3) {
        this.a3 = a3;
    }

    public Integer getA4() {
        return a4;
    }

    public void setA4(Integer a4) {
        this.a4 = a4;
    }

    public Integer getQid() {
        return qid;
    }

    public void setQid(Integer qid) {
        this.qid = qid;
    }
}
