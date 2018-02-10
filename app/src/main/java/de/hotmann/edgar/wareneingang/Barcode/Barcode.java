package de.hotmann.edgar.wareneingang.Barcode;

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
    Barcode(String season,
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
    String getSeason() {return season;}
    String getStyle() {return style;}
    String getQuality() {return quality;}
    String getLgd() {return lgd;}
    String getColour() {return colour;}
    String getSize() {return size;}
    String getItemname() {return itemname;}
    long getId() {return id;}
    @Override
    public String toString() {
        String lengthcode;
        if(lgd=="0") {
            lengthcode = null;
        }else if(lgd == "1"){
            lengthcode="K";
        }else if(lgd=="2"){
            lengthcode="L";
        }else {
            lengthcode="FEHLER";
        }
            return season + " " + style + "-" + quality + " Fb. " + colour + " Gr. " + size + lengthcode + " : " + eanno;

    }
}
