package com.example.onarimtakip.Tanimlamalar.VeriTabani;

public class YedekParca {

    public YedekParca() {

    }

    int id, marka_id, model_id;
    int yedekparcatipi_id;

    String parca_adi, aciklama;

    double fiyat;

    int stokmin, stok_miktar, silindi;

    public int getYedekparcatipi_id() {
        return yedekparcatipi_id;
    }

    public void setYedekparcatipi_id(int yedekparcatipi_id) {
        this.yedekparcatipi_id = yedekparcatipi_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMarka_id() {
        return marka_id;
    }

    public void setMarka_id(int marka_id) {
        this.marka_id = marka_id;
    }

    public int getModel_id() {
        return model_id;
    }

    public void setModel_id(int model_id) {
        this.model_id = model_id;
    }

    public String getParca_adi() {
        return parca_adi;
    }

    public void setParca_adi(String parca_adi) {
        this.parca_adi = parca_adi;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public double getFiyat() {
        return fiyat;
    }

    public void setFiyat(double fiyat) {
        this.fiyat = fiyat;
    }

    public int getStokmin() {
        return stokmin;
    }

    public void setStokmin(int stokmin) {
        this.stokmin = stokmin;
    }

    public int getStok_miktar() {
        return stok_miktar;
    }

    public void setStok_miktar(int stok_miktar) {
        this.stok_miktar = stok_miktar;
    }

    public int getSilindi() {
        return silindi;
    }

    public void setSilindi(int silindi) {
        this.silindi = silindi;
    }
}
