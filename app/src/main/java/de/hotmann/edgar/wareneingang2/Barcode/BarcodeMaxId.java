package de.hotmann.edgar.wareneingang2.Barcode;

/**
 * Created by Edgar Hotmann on 11.02.2018.
 */

public class BarcodeMaxId {

    private int maximum;

    public BarcodeMaxId(int maximum) {
        this.maximum = maximum;
    }

    public int getMaximum() { return maximum; }

    @Override
    public String toString() {
        String output = Integer.toString(maximum);
        //String output = "Palette: " + palette + " " + season + " " + style + "-" + quality + " Fb. " + colour + " Gr. " + size + " x " + quantsum;
        return output;
    }

    public int getMaxId() {
        return maximum;
    }


}
