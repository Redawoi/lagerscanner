package de.hotmann.edgar.wareneingang2.Barcode;

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
    private String productgroup;
    Barcode(String season,
            String style,
            String quality,
            String lgd,
            String colour,
            String size,
            String eanno,
            String itemname,
            long id,
            String productgroup) {
        this.season = season;
        this.style = style;
        this.quality = quality;
        this.lgd = lgd;
        this.colour = colour;
        this.size = size;
        this.eanno = eanno;
        this.itemname = itemname;
        this.id = id;
        this.productgroup = productgroup;
    }
    String getSeason() {return season;}

    public void setSeason(String season) {
        this.season = season;
    }

    String getStyle() {return style;}

    public void setStyle(String style) {
        this.style = style;
    }

    String getQuality() {return quality;}

    public void setQuality(String quality) {
        this.quality = quality;
    }

    String getLgd() {return lgd;}

    String getColour() {return colour;}

    public void setColour(String colour) {
        this.colour = colour;
    }

    String getSize() {return size;}

    public void setSize(String size) {
        this.size = size;
    }

    String getEanno() {return eanno; }

    String getItemname() {return itemname;}

    String getProductgroup() { return productgroup; }

    long getId() {return id;}

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        String lengthcode;
        switch (lgd) {
            case "0":
                lengthcode = null;
                break;
            case "1":
                lengthcode = "K";
                break;
            case "2":
                lengthcode = "L";
                break;
            default:
                lengthcode = "FEHLER";
                break;
        }
            return season + " " + style + "-" + quality + " Fb. " + colour + " Gr. " + size + lengthcode + " : " + eanno;

    }
}
