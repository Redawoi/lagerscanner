package de.hotmann.edgar.wareneingang.Eingang;

/**
 * Created by edgar on 25.05.2016.
 */
public class EingangSumKartons {

    private int palette;
    private boolean counttopallet;
    private int kartonanzahl;
    private long id;

    public EingangSumKartons(int palette,
                             boolean counttopallet,
                             int kartonanzahl,
                             long id) {
        this.palette = palette;
        this.counttopallet = counttopallet;
        this.kartonanzahl = kartonanzahl;
        this.id = id;
    }

    public int getKartonanzahl() { return kartonanzahl;}
    public void setKartonanzahl(int kartonanzahl) {this.kartonanzahl = kartonanzahl;}
    public int getPalette() {return palette;}
    public void setPalette(int palette) {this.palette=palette;}
    public boolean getCounttoPallet() { return counttopallet;}
    public void setCounttopallet(boolean counttopallet) { this.counttopallet = counttopallet; }
    public long getId() {return id;}
    public void setId(long id) {this.id = id;}

    @Override
    public String toString() {
        String output = kartonanzahl + "/24 Kartons";
        //String output = "Palette: " + palette + " " + season + " " + style + "-" + quality + " Fb. " + colour + " Gr. " + size + " x " + quantsum;

        return output;
    }
}
