package com.example.aplikasi_interior;

public class CartProduct {
    private int id_brg;
    private int user_id;
    private int qty;


    public CartProduct(int id_brg, int user_id, int qty) {
        this.id_brg = id_brg;
        this.user_id = user_id;
        this.qty = qty;
    }

    public int getId_brg() {
        return id_brg;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getQty() {
        return qty;
    }
}
