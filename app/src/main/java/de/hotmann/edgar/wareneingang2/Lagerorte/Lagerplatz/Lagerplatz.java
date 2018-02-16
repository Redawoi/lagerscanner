package de.hotmann.edgar.wareneingang2.Lagerorte.Lagerplatz;

/**
 * Erstellt von Edgar Hotmann am 15.02.2018.
 */

public class Lagerplatz {
    private String season;
    private String style;
    private String quality;
    private String lgd;
    private String colour;
    private String size;
    private boolean hanging;
    private int halle;
    private int ebene;
    private int reihe;
    Lagerplatz(
            String season,
            String style,
            String quality,
            String lgd,
            String colour,
            String size,
            boolean hanging,
            int halle,
            int ebene,
            int reihe
    ) {
        this.season = season;
        this.style = style;
        this.quality = quality;
        this.lgd = lgd;
        this.colour = colour;
        this.size = size;
        this.hanging = hanging;
        this.halle = halle;
        this. ebene = ebene;
        this.reihe = reihe;
    }

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

    public String getLgd() {
        return lgd;
    }

    public void setLgd(String lgd) {
        this.lgd = lgd;
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

    public boolean isHanging() {
        return hanging;
    }

    public void setHanging(boolean hanging) {
        this.hanging = hanging;
    }

    public int getHalle() {
        return halle;
    }

    public void setHalle(int halle) {
        this.halle = halle;
    }

    public int getEbene() {
        return ebene;
    }

    public void setEbene(int ebene) {
        this.ebene = ebene;
    }

    public int getReihe() {
        return reihe;
    }

    public void setReihe(int reihe) {
        this.reihe = reihe;
    }
}
