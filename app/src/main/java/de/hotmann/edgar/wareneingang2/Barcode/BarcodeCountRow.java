package de.hotmann.edgar.wareneingang2.Barcode;

/**
 * Created by Edgar Hotmann on 12.02.2018.
 */

public class BarcodeCountRow {
    private int rows;

    public BarcodeCountRow(int maximum) {
        this.rows = maximum;
    }

    @Override
    public String toString() {
        String output = Integer.toString(rows);
        return output;
    }

    public int getRowCount() {
        return rows;
    }
}
