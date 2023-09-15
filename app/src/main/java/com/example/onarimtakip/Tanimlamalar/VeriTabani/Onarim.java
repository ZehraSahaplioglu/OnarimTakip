package com.example.onarimtakip.Tanimlamalar.VeriTabani;

import java.util.Date;

public class Onarim {

    int id, cihaz_id, isyeri_id;
    int onarimdurum_id, toplam_sure;
    float toplam_maliyet;
    Date baslama_tarihi, bitis_tarihi;
    int ariza_tipi_id, marka_id, model_id;
    int onarim_bitti, silindi;
    String aciklama;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCihaz_id() {
        return cihaz_id;
    }

    public void setCihaz_id(int cihaz_id) {
        this.cihaz_id = cihaz_id;
    }

    public int getIsyeri_id() {
        return isyeri_id;
    }

    public void setIsyeri_id(int isyeri_id) {
        this.isyeri_id = isyeri_id;
    }

    public int getOnarimdurum_id() {
        return onarimdurum_id;
    }

    public void setOnarimdurum_id(int onarimdurum_id) {
        this.onarimdurum_id = onarimdurum_id;
    }

    public int getToplam_sure() {
        return toplam_sure;
    }

    public void setToplam_sure(int toplam_sure) {
        this.toplam_sure = toplam_sure;
    }

    public float getToplam_maliyet() {
        return toplam_maliyet;
    }

    public void setToplam_maliyet(float toplam_maliyet) {
        this.toplam_maliyet = toplam_maliyet;
    }

    public Date getBaslama_tarihi() {
        return baslama_tarihi;
    }

    public void setBaslama_tarihi(Date baslama_tarihi) {
        this.baslama_tarihi = baslama_tarihi;
    }

    public Date getBitis_tarihi() {
        return bitis_tarihi;
    }

    public void setBitis_tarihi(Date bitis_tarihi) {
        this.bitis_tarihi = bitis_tarihi;
    }

    public int getAriza_tipi_id() {
        return ariza_tipi_id;
    }

    public void setAriza_tipi_id(int ariza_tipi_id) {
        this.ariza_tipi_id = ariza_tipi_id;
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

    public int getOnarim_bitti() {
        return onarim_bitti;
    }

    public void setOnarim_bitti(int onarim_bitti) {
        this.onarim_bitti = onarim_bitti;
    }

    public int getSilindi() {
        return silindi;
    }

    public void setSilindi(int silindi) {
        this.silindi = silindi;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public Onarim() {
    }
}
