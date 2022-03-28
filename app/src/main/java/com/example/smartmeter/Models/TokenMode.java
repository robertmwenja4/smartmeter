package com.example.smartmeter.Models;

public class TokenMode {

    private String month;
    private String tarrif;
    private double consumption;
    private double fcc;
    private double ferfa;
    private double inflation;
    private double warma;
    private double erc;
    private double rep;
    private double vat;

    public TokenMode() {
    }

    public TokenMode(String month, String tarrif, double consumption, double fcc, double ferfa, double inflation, double warma, double erc, double rep, double vat) {
        this.month = month;
        this.tarrif = tarrif;
        this.consumption = consumption;
        this.fcc = fcc;
        this.ferfa = ferfa;
        this.inflation = inflation;
        this.warma = warma;
        this.erc = erc;
        this.rep = rep;
        this.vat = vat;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getTarrif() {
        return tarrif;
    }

    public void setTarrif(String tarrif) {
        this.tarrif = tarrif;
    }

    public double getConsumption() {
        return consumption;
    }

    public void setConsumption(double consumption) {
        this.consumption = consumption;
    }

    public double getFcc() {
        return fcc;
    }

    public void setFcc(double fcc) {
        this.fcc = fcc;
    }

    public double getFerfa() {
        return ferfa;
    }

    public void setFerfa(double ferfa) {
        this.ferfa = ferfa;
    }

    public double getInflation() {
        return inflation;
    }

    public void setInflation(double inflation) {
        this.inflation = inflation;
    }

    public double getWarma() {
        return warma;
    }

    public void setWarma(double warma) {
        this.warma = warma;
    }

    public double getErc() {
        return erc;
    }

    public void setErc(double erc) {
        this.erc = erc;
    }

    public double getRep() {
        return rep;
    }

    public void setRep(double rep) {
        this.rep = rep;
    }

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }
}
