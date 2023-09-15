package com.example.onarimtakip.Tanimlamalar.VeriTabani;

public class Model {

    int id, marka_id, silindi;
    String model_adi, aciklama;

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

    public int getSilindi() {
        return silindi;
    }

    public void setSilindi(int silindi) {
        this.silindi = silindi;
    }

    public String getModel_adi() {
        return model_adi;
    }

    public void setModel_adi(String model_adi) {
        this.model_adi = model_adi;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public Model() {
    }
}
