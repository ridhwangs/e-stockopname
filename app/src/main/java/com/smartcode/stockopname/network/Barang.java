package com.smartcode.stockopname.network;

public class Barang {
    private int id;
    private String lokasi, kode_barang, jumlah_barang;

    public Barang(int id, String lokasi, String kode_barang, String jumlah_barang) {
        this.id = id;
        this.lokasi = lokasi;
        this.kode_barang = kode_barang;
        this.jumlah_barang = jumlah_barang;
    }

    public int getId() {
        return id;
    }

    public String getLokasi() {
        return lokasi;
    }

    public String getKode_barang() {
        return kode_barang;
    }

    public String getJumlah_barang() {
        return jumlah_barang;
    }
}
