package com.example.onarimtakip.Tanimlamalar.VeriTabani;

import java.util.Date;

public class OnarimHareket {

    int id;
    int onarim_id;
    int silindi;
    int parcaadet;
    int onarim_hareket_tipi_id, harcananyedekparca_id;
    Date hareket_tarihi;
    String aciklama;


    public int getParcaadet() {
        return parcaadet;
    }

    public void setParcaadet(int parcaadet) {
        this.parcaadet = parcaadet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOnarim_id() {
        return onarim_id;
    }

    public void setOnarim_id(int onarim_id) {
        this.onarim_id = onarim_id;
    }

    public int getSilindi() {
        return silindi;
    }

    public void setSilindi(int silindi) {
        this.silindi = silindi;
    }

    public int getOnarim_hareket_tipi_id() {
        return onarim_hareket_tipi_id;
    }

    public void setOnarim_hareket_tipi_id(int onarim_hareket_tipi_id) {
        this.onarim_hareket_tipi_id = onarim_hareket_tipi_id;
    }

    public int getHarcananyedekparca_id() {
        return harcananyedekparca_id;
    }

    public void setHarcananyedekparca_id(int harcananyedekparca_id) {
        this.harcananyedekparca_id = harcananyedekparca_id;
    }

    public Date getHareket_tarihi() {
        return hareket_tarihi;
    }

    public void setHareket_tarihi(Date hareket_tarihi) {
        this.hareket_tarihi = hareket_tarihi;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public OnarimHareket() {
    }
}
