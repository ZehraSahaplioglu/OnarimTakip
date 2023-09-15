package com.example.onarimtakip.Tanimlamalar.VeriTabani;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Yazici {
    SQLiteDatabase db ;


// Bu sınıfta yazılan fonksiyonlar birçok yerde kullanılıyor, kod tekrarını önlemek ve okunabilirliği arttırmak için oluşturuldu.

    public Yazici(Context cntx) {
        TabanHelper t1=new TabanHelper(cntx);
        db=t1.getWritableDatabase();
    }

    DateFormat tcdd_DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // ************************* KAYDETME ISLEMLERI ICIN

    // android sürüm, ekran boyutu, yedek parça, arıza tipi ve onarım durumu için kullanılır.
    public void tipYaz(String adi, String aciklama, int tipgrupid){

        ContentValues cv= new ContentValues();
        cv.put("tipgrupid" ,tipgrupid);
        cv.put("adi", adi);
        cv.put("aciklama", aciklama);
        cv.put("silindi", 0);
        db.insert("DINAMIK_TIPLER",null,cv);
    }

    public void isyeriYaz(String adi, String aciklama){

        ContentValues cv= new ContentValues();
        cv.put("isyeri_adi", adi);
        cv.put("aciklama", aciklama);
        cv.put("silindi", 0);
        db.insert("ISYERI",null,cv);
    }

    public void modelYaz(String adi, String aciklama, int markaid){

        ContentValues cv= new ContentValues();
        cv.put("model_adi", adi);
        cv.put("marka_id",markaid);
        cv.put("aciklama", aciklama);
        cv.put("silindi", 0);
        db.insert("MODEL",null,cv);
    }

    public void markaYaz(String adi, String aciklama){

        ContentValues cv= new ContentValues();
        cv.put("marka_adi", adi);
        cv.put("aciklama", aciklama);
        cv.put("silindi", 0);
        db.insert("MARKA",null,cv);
    }

    public void cihazYaz (String envno, int marka_id, int model_id, int androidsurum_id, int isyeri_id, String gsm_no,
                          int ekranboyutu_id, String imei_no, String aciklama ){

        ContentValues cv= new ContentValues();
        cv.put("envno", envno);
        cv.put("marka_id", marka_id);
        cv.put("model_id", model_id);
        cv.put("androidsurum_id",androidsurum_id);
        cv.put("isyeri_id",isyeri_id);
        cv.put("gsm_no",gsm_no);
        cv.put("ekranboyutu_id",ekranboyutu_id);
        cv.put("imei_no",imei_no);
        cv.put("aciklama",aciklama);
        cv.put("silindi", 0);
        db.insert("CIHAZ",null,cv);
    }

    public  void yedekparcaYaz (int marka_id, int model_id, String parca_adi, double fiyat, int stokmin, int stok_miktar,
                                String aciklama, int yeddekparcatipi_id){

        ContentValues cv= new ContentValues();
        cv.put("marka_id", marka_id);
        cv.put("model_id", model_id);
        cv.put("parca_adi", parca_adi);
        cv.put("fiyat", fiyat);
        cv.put("stokmin", stokmin);
        cv.put("stok_miktar", stok_miktar);
        cv.put("aciklama",aciklama);
        cv.put("silindi", 0);
        cv.put("yeddekparcatipi_id", yeddekparcatipi_id);
        db.insert("YEDEKPARCA",null,cv);
    }

    // *************************** Onarim işlemleri sayfası için:

    public void onarimKaydet(int cihaz_id, int isyeri_id, int onarimdurum_id, float toplam_sure, int toplam_maliyet,
                              int ariza_tipi_id, int marka_id, int model_id, int onarim_bitti, String aciklama){

        ContentValues cv= new ContentValues();
        cv.put("cihaz_id", cihaz_id);
        cv.put("isyeri_id", isyeri_id);
        cv.put("onarimdurum_id", onarimdurum_id);
        cv.put("toplam_sure", toplam_sure);
        cv.put("toplam_maliyet", toplam_maliyet);
        cv.put("baslama_tarihi", tcdd_DateFormat.format(Calendar.getInstance().getTime()));
        cv.put("bitis_tarihi", tcdd_DateFormat.format(Calendar.getInstance().getTime()));
        cv.put("ariza_tipi_id", ariza_tipi_id);
        cv.put("marka_id", marka_id);
        cv.put("model_id", model_id);
        cv.put("onarim_bitti", onarim_bitti);
        cv.put("aciklama", aciklama);
        cv.put("silindi", 0);
        db.insert("ONARIM",null,cv);
    }

    // ************************** yeni tamir gir sayfası için
    public void yeniTamirGirKaydet( int onarim_id, int onarim_hareket_tipi_id, int harcananyedekparca_id, String aciklama){

        ContentValues cv= new ContentValues();
        cv.put("onarim_id", onarim_id);
        cv.put("onarim_hareket_tipi_id", onarim_hareket_tipi_id);
        cv.put("harcananyedekparca_id", harcananyedekparca_id);
        cv.put("hareket_tarihi", tcdd_DateFormat.format(Calendar.getInstance().getTime()));
        cv.put("aciklama", aciklama);
        cv.put("silindi", 0);
        cv.put("parcaadet", 0);
        db.insert("ONARIMHAREKET",null,cv);

    }

    // ****************************** GUNCELLEME ISLEMLERI ICIN

    public void cihazGuncelle(String envno, int marka_id, int model_id, int androidsurum_id, int isyeri_id,
                              String gsm_no, int ekranboyutu_id, String imei_no, String aciklama, int cihazid){

        ContentValues cv= new ContentValues();
        cv.put("envno", envno);
        cv.put("marka_id", marka_id);
        cv.put("model_id", model_id);
        cv.put("androidsurum_id",androidsurum_id);
        cv.put("isyeri_id",isyeri_id);
        cv.put("gsm_no",gsm_no);
        cv.put("ekranboyutu_id",ekranboyutu_id);
        cv.put("imei_no",imei_no);
        cv.put("aciklama",aciklama);
        cv.put("silindi", 0);
        db.update("CIHAZ", cv, "id = ?", new String[]{ String.valueOf(cihazid) });
    }

    public void yedekParcaGuncelle(int parca_id, int marka_id, int model_id, String parca_adi, double fiyat, int stokmin,
                                   int stok_miktar, String aciklama, int yeddekparcatipi_id ){

        ContentValues cv= new ContentValues();
        cv.put("marka_id", marka_id);
        cv.put("model_id", model_id);
        cv.put("parca_adi", parca_adi);
        cv.put("fiyat", fiyat);
        cv.put("stokmin", stokmin);
        cv.put("stok_miktar", stok_miktar);
        cv.put("yeddekparcatipi_id", yeddekparcatipi_id);
        cv.put("aciklama", aciklama);
        cv.put("silindi", 0);
        db.update("YEDEKPARCA", cv, "id = ?", new String[]{ String.valueOf(parca_id) });

    }
    public void yedekParcaStokGuncelle(int parca_id, int stok_miktar ){

        ContentValues cv= new ContentValues();
        cv.put("stok_miktar", stok_miktar);

        db.update("YEDEKPARCA", cv, "id = ?", new String[]{ String.valueOf(parca_id) });

    }

    public void modelGuncelle(String adi, String aciklama, int markaid, int modelid){

        ContentValues cv= new ContentValues();
        cv.put("model_adi", adi);
        cv.put("marka_id",markaid);
        cv.put("aciklama", aciklama);
        cv.put("silindi", 0);
        db.update("MODEL", cv, "id = ?", new String[]{ String.valueOf(modelid) });
    }

    public void markaGuncelle(String adi, String aciklama, int markaid){

        ContentValues cv= new ContentValues();
        cv.put("marka_adi", adi);
        cv.put("aciklama", aciklama);
        cv.put("silindi", 0);
        db.update("MARKA", cv, "id = ?", new String[]{ String.valueOf(markaid) });
    }

    public void isyeriGuncelle(String adi, String aciklama, int isyeriid){

        ContentValues cv= new ContentValues();
        cv.put("isyeri_adi", adi);
        cv.put("aciklama", aciklama);
        cv.put("silindi", 0);
        db.update("ISYERI", cv, "id = ?", new String[]{ String.valueOf(isyeriid) });
    }

    public void tiptanimlamaGuncelle(String adi, String aciklama, int tipgrupid){

        ContentValues cv= new ContentValues();
        //cv.put("tipgrupid" ,tipgrupid);
        cv.put("adi", adi);
        cv.put("aciklama", aciklama);
        cv.put("silindi", 0);
        db.update("DINAMIK_TIPLER", cv, "id = ?", new String[]{ String.valueOf(tipgrupid) });
    }

    public void yeniTamirGirGuncelle( int onarim_id, int onarim_hareket_tipi_id, int harcananyedekparca_id,
                                      String aciklama,int onarimHareketId,int parcaadedi){

        ContentValues cv= new ContentValues();
        cv.put("onarim_id", onarim_id);
        cv.put("onarim_hareket_tipi_id", onarim_hareket_tipi_id);
        cv.put("harcananyedekparca_id", harcananyedekparca_id);
        cv.put("aciklama", aciklama);
        cv.put("silindi", 0);
        cv.put("parcaadet", parcaadedi);

        db.update("ONARIMHAREKET", cv, "id = ?", new String[]{ String.valueOf(onarimHareketId) });
    }

    public void onarimGuncelle(int cihaz_id, int isyeri_id, int onarimdurum_id, float toplam_sure, int toplam_maliyet,
                             int ariza_tipi_id, int marka_id, int model_id, int onarim_bitti, String aciklama,int onarim_id){

        ContentValues cv= new ContentValues();
        cv.put("cihaz_id", cihaz_id);
        cv.put("isyeri_id", isyeri_id);
        cv.put("onarimdurum_id", onarimdurum_id);
        cv.put("toplam_sure", toplam_sure);
        cv.put("toplam_maliyet", toplam_maliyet);
        cv.put("baslama_tarihi", tcdd_DateFormat.format(Calendar.getInstance().getTime()));
        cv.put("bitis_tarihi", tcdd_DateFormat.format(Calendar.getInstance().getTime()));
        cv.put("ariza_tipi_id", ariza_tipi_id);
        cv.put("marka_id", marka_id);
        cv.put("model_id", model_id);
        cv.put("onarim_bitti", onarim_bitti);
        cv.put("aciklama", aciklama);
        cv.put("silindi", 0);
        db.update("ONARIM", cv, "id = ?", new String[]{ String.valueOf(onarim_id) });
    }

    public void onarimSonlandir( int onarimdurum_id, int toplam_sure, double toplam_maliyet,
                               int ariza_tipi_id, int onarim_bitti, String aciklama,int onarim_id){

        ContentValues cv= new ContentValues();


        cv.put("onarimdurum_id", onarimdurum_id);
        cv.put("toplam_sure", toplam_sure);
        cv.put("toplam_maliyet", toplam_maliyet);
        cv.put("bitis_tarihi", tcdd_DateFormat.format(Calendar.getInstance().getTime()));
        cv.put("ariza_tipi_id", ariza_tipi_id);
        cv.put("onarim_bitti", onarim_bitti);
        cv.put("aciklama", aciklama);
        db.update("ONARIM", cv, "id = ?", new String[]{ String.valueOf(onarim_id) });
    }

    // ******************************** SILME ISLEMLERI ICIN

    public void cihazSil( int cihazid){
        ContentValues cv= new ContentValues();
        cv.put("silindi", 1);
        db.update("CIHAZ", cv, "id = ?", new String[]{ String.valueOf(cihazid) });
    }

    public void modelSil( int modelid){
        ContentValues cv= new ContentValues();
        cv.put("silindi", 1);
        db.update("MODEL", cv, "id = ?", new String[]{ String.valueOf(modelid) });
    }

    public void markaSil( int markaid){
        ContentValues cv= new ContentValues();
        cv.put("silindi", 1);
        db.update("MARKA", cv, "id = ?", new String[]{ String.valueOf(markaid) });
    }

    public void isyeriSil( int isyeriid){
        ContentValues cv= new ContentValues();
        cv.put("silindi", 1);
        db.update("ISYERI", cv, "id = ?", new String[]{ String.valueOf(isyeriid) });
    }

    public void yedekParcaSil( int parcaid){
        ContentValues cv= new ContentValues();
        cv.put("silindi", 1);
        db.update("YEDEKPARCA", cv, "id = ?", new String[]{ String.valueOf(parcaid) });
    }

    public void tiptanimlamaSil( int tiptanimlamaid){
        ContentValues cv= new ContentValues();
        cv.put("silindi", 1);
        db.update("DINAMIK_TIPLER", cv, "id = ?", new String[]{ String.valueOf(tiptanimlamaid) });
    }

    public void onarimSil( int onarim_id){
        ContentValues cv= new ContentValues();
        cv.put("silindi", 1);
        db.update("ONARIM", cv, "id = ?", new String[]{ String.valueOf(onarim_id) });
    }

    public void onarimHareketSil( int onarim_id){
        ContentValues cv= new ContentValues();
        cv.put("silindi", 1);
        db.update("ONARIMHAREKET", cv, "id = ?", new String[]{ String.valueOf(onarim_id) });
    }

}
