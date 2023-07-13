package com.example.aplikasi_interior;
import java.io.Serializable;
public class Item implements Serializable {
    private String idBrg;
    private int qty;
    private double harga;

    public Item(String idBrg, int qty, double harga) {
        this.idBrg = idBrg;
        this.qty = qty;
        this.harga = harga;
    }

    public String getIdBrg() {
        return idBrg;
    }

    public int getQty() {
        return qty;
    }

    public double getHarga() {
        return harga;
    }
}
