package de.hotmann.edgar.wareneingang.Eingang;

/**
 * Created by edgar on 25.05.2016.
 */
public class Eingang {

    private int palette;
    private String season, style, quality, colour, size,lgd;
    private int quantity, quantsum, day, month, year, week;
    private boolean secondarychoice;
    private boolean counttopallet;
    private long id;

    public Eingang(int palette,
                   String season,
                   String style,
                   String quality,
                   String colour,
                   String size,
                   String lgd,
                   int quantity,
                   int quantsum,
                   boolean secondarychoice,
                   boolean counttopallet,
                   int day,
                   int month,
                   int year,
                   int week,
                   long id) {
        this.palette = palette;
        this.season = season;
        this.style = style;
        this.quality = quality;
        this.colour = colour;
        this.size = size;
        this.lgd = lgd;
        this.quantity = quantity;
        this.quantsum = quantsum;
        this.secondarychoice = secondarychoice;
        this.counttopallet = counttopallet;
        this.day = day;
        this.month = month;
        this.year = year;
        this.week = week;
        this.id = id;
    }

    public int getPalette() {return palette;}
    public void setPalette(int palette) {this.palette=palette;}

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

    public int getQuantity() {
        return quantity;
    }

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

    public int getQuantsum() { return quantsum; }

    public boolean getSecondarychoice() { return secondarychoice; }

    public void setSecondarychoice(boolean secondarychoice) { this.secondarychoice = secondarychoice; }

    public boolean getCounttoPallet() { return counttopallet;}

    public void setCounttopallet(boolean counttopallet) { this.counttopallet = counttopallet; }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
       String output = day + "." + month + "." + year + " S." + season + " " + style + " " + quality + " Fb. " + colour + " Gr. " + size + " x " + quantity;
        //String output = "Palette: " + palette + " " + season + " " + style + "-" + quality + " Fb. " + colour + " Gr. " + size + " x " + quantsum;

        return output;
    }
}
