/**
 * Egyszeru POJO osztaly a Course entitasokhoz
 */

package all;

import javax.persistence.*;
import javax.persistence.Table;

@Entity
@Table(name = "oratabla")
public class Course {

    //adattagok amik az elozetes orarendben szereplo oszlopoknak felelnek meg
    private long id;
    private int felev;
    private String kar;
    private String szki;
    private String ti;
    private String tantargy;
    private String tanszek;
    private String eloado;
    private String csoport;
    private int fo;
    private int kezdes;
    private int hossz;
    private String terem;
    private String nap;
    private String tipus;

    public Course() {

    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public int getFelev() {
        return felev;
    }

    public void setFelev(int felev) {
        this.felev = felev;
    }

    public String getKar() {
        return kar;
    }

    public void setKar(String kar) {
        this.kar = kar;
    }

    public String getSzki() {
        return szki;
    }

    public void setSzki(String szki) {
        this.szki = szki;
    }

    public String getTi() {
        return ti;
    }

    public void setTi(String ti) {
        this.ti = ti;
    }

    public String getTantargy() {
        return tantargy;
    }

    public void setTantargy(String tantargy) {
        this.tantargy = tantargy;
    }

    public String getTanszek() {
        return tanszek;
    }

    public void setTanszek(String tanszek) {
        this.tanszek = tanszek;
    }

    public String getEloado() {
        return eloado;
    }

    public void setEloado(String eloado) {
        this.eloado = eloado;
    }

    public String getCsoport() {
        return csoport;
    }

    public void setCsoport(String csoport) {
        this.csoport = csoport;
    }

    public int getFo() {
        return fo;
    }

    public void setFo(int fo) {
        this.fo = fo;
    }

    public int getKezdes() {
        return kezdes;
    }

    public void setKezdes(int kezdes) {
        this.kezdes = kezdes;
    }

    public int getHossz() {
        return hossz;
    }

    public void setHossz(int hossz) {
        this.hossz = hossz;
    }

    public String getTerem() {
        return terem;
    }

    public void setTerem(String terem) {
        this.terem = terem;
    }

    public String getNap() {
        return nap;
    }

    public void setNap(String nap) {
        this.nap = nap;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    @Override
    public String toString() {
        return "CourseDB{" +
                "id=" + id +
                ", felev=" + felev +
                ", kar='" + kar + '\'' +
                ", szki='" + szki + '\'' +
                ", ti='" + ti + '\'' +
                ", tantargy='" + tantargy + '\'' +
                ", tanszek='" + tanszek + '\'' +
                ", eloado='" + eloado + '\'' +
                ", csoport='" + csoport + '\'' +
                ", fo=" + fo +
                ", kezdes=" + kezdes +
                ", hossz=" + hossz +
                ", terem='" + terem + '\'' +
                ", nap='" + nap + '\'' +
                ", tipus='" + tipus + '\'' +
                '}';
    }
}
