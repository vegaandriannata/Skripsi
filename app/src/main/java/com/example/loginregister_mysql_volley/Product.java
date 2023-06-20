package com.example.loginregister_mysql_volley;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

public class Product implements Serializable {
    private transient Bitmap image;
    private byte[] imageBytes;
    private String name;
    private String price;

    public Product(Bitmap image, String name, String price) {
        this.image = image;
        this.name = name;
        this.price = price;
    }

    public Bitmap getImage() {
        if (image == null && imageBytes != null) {
            image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        }
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        imageBytes = byteArrayOutputStream.toByteArray();
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
}
