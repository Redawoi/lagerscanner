package de.hotmann.edgar.wareneingang.Barcode;

/**
 * Created by edgar on 25.05.2016.
 */
public class Barcode {

    private String season;
    private String style;
    private String quality;
    private String lgd;
    private String colour;
    private String size;
    private String eanno;
    private String itemname;

    private long id;

    public Barcode(String season,
                   String style,
                   String quality,
                   String lgd,
                   String colour,
                   String size,
                   String eanno,
                   String itemname,
                   long id) {
        this.season = season;
        this.style = style;
        this.quality = quality;
        this.lgd = lgd;
        this.colour = colour;
        this.size = size;
        this.eanno = eanno;
        this.itemname = itemname;
        this.id = id;
    }

    public String getSeason() {return season;}
    public void setSeason(String season) {this.season = season;}
    public String getStyle() {return style;}
    public void setStyle(String style) {this.style = style;}
    public String getQuality() {return quality;}
    public void setQuality(String quality) {this.quality = quality;}
    public String getLgd() {return lgd;}
    public void setLgd(String lgd) {this.lgd = lgd;}
    public String getColour() {return colour;}
    public void setColour(String colour) {this.colour = colour;}
    public String getSize() {return size;}
    public void setSize(String size) {this.size = size;}
    public String getEanno() {return eanno;}
    public void setEanno(String eanno) {this.eanno=eanno;}
    public String getItemname() {return itemname;}
    public void setItemname(String itemname) {this.itemname=itemname;}
    public long getId() {return id;}
    public void setId(long id) {this.id = id;}

    @Override
    public String toString() {
        String output = season + " " + style + "-" + quality + " Fb. " + colour + " Gr. " + size + " : " + eanno;

        return output;
    }
}
