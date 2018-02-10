package de.hotmann.edgar.wareneingang.Eingang;

import java.util.Objects;

public class Eingangwosum {

    private int palette;
    private String season, style, quality, colour, size,lgd;
    private int quantity;
    private int day;
    private int month;
    private int year;
    private boolean secondarychoice;
    private boolean counttopallet;
    private long id;

    Eingangwosum(int palette,
                 String season,
                 String style,
                 String quality,
                 String colour,
                 String size,
                 String lgd,
                 int quantity,
                 boolean secondarychoice,
                 boolean counttopallet,
                 int day,
                 int month,
                 int year,
                 long id) {
        this.palette = palette;
        this.season = season;
        this.style = style;
        this.quality = quality;
        this.colour = colour;
        this.size = size;
        this.lgd = lgd;
        this.quantity = quantity;
        this.secondarychoice = secondarychoice;
        this.counttopallet = counttopallet;
        this.day = day;
        this.month = month;
        this.year = year;
        this.id = id;
    }

    int getPalette() {return palette;}
    // public void setPalette(int palette) {this.palette=palette;}

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getLgd() {
        return lgd;
    }

    public void setLgd(String lgd) {
        this.lgd = lgd;
    }

    int getQuantity() {
        return quantity;
    }

    /*



    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getDay() { return day; }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() { return month; }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() { return year; }

    public void setYear(int year) { this.year = year; }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }


    */

    boolean getSecondarychoice() { return secondarychoice; }

    // public void setSecondarychoice(boolean secondarychoice) { this.secondarychoice = secondarychoice; }

    boolean getCounttoPallet() { return counttopallet;}

    // public void setCounttopallet(boolean counttopallet) { this.counttopallet = counttopallet; }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private String zweitewahlchecker() {
        String ergebnis;
        if(getSecondarychoice()) {
            ergebnis=" II.W";
        }else{
            ergebnis="";
        }
        return ergebnis;
    }

    @Override
    public String toString() {
        //String output = "Palette: " + palette + " " + season + " " + style + "-" + quality + " Fb. " + colour + " Gr. " + size + " x " + quantsum;
        String lengthcode;
        if(lgd.equals("0")) {
            lengthcode = "";
        }else if(lgd.equals("1")){
            lengthcode="K";
        }else if(lgd.equals("2")){
            lengthcode="L";
        }else {
            lengthcode="FEHLER";
        }
        return day + "." + month + "." + year + " S." + season + " " + style + " " + quality + " Fb. " + colour + " Gr. " + size + lengthcode + " x " + quantity + zweitewahlchecker();
    }
}
