package com.example.onarimtakip.Tanimlamalar.VeriTabani;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class TabanHelper extends SQLiteOpenHelper {
    public TabanHelper(@Nullable Context context) {
        super(context, "OnarimDB_10.db", null, 1);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String qq1="CREATE TABLE CIHAZ("+
                "id"+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                "envno"+" TEXT , "+
                "marka_id"+" INTEGER ,"+
                "model_id"+" INTEGER ,"+
                "androidsurum_id"+" INTEGER ,"+
                "isyeri_id"+" INTEGER ,"+
                "gsm_no"+" TEXT ," +
                "ekranboyutu_id"+" INTEGER ,"+
                "imei_no"+" TEXT ,"+
                "aciklama"+" TEXT ,"+
                "silindi"+" INTEGER"+ ")";
        db.execSQL(qq1);

        String qq2="CREATE TABLE MARKA("+
                "id"+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                "marka_adi"+" TEXT,"+
                "aciklama"+" TEXT,"+
                "silindi"+" INTEGER" +")";
        db.execSQL(qq2);

        String qq3="CREATE TABLE MODEL("+
                "id"+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                "model_adi"+" TEXT, "+
                "marka_id"+" INTEGER ,"+
                "aciklama"+" TEXT ,"+
                "silindi"+" INTEGER" +")";
        db.execSQL(qq3);

        String qq4="CREATE TABLE ISYERI("+
                "id"+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                "isyeri_adi"+" TEXT , "+
                "aciklama"+" TEXT ,"+
                "silindi"+" INTEGER" +")";
        db.execSQL(qq4);

        String qq5="CREATE TABLE YEDEKPARCA("+
                "id"+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                "yeddekparcatipi_id"+" INTEGER , " +
                "marka_id"+" INTEGER , "+
                "model_id"+" INTEGER ,"+
                "parca_adi"+" TEXT ,"+
                "fiyat"+" REAL ," +
                "stokmin"+" INTEGER ,"+
                "stok_miktar"+" INTEGER ,"+
                "aciklama"+" TEXT ,"+
                "silindi"+" INTEGER" +")";
        db.execSQL(qq5);

        String qq6="CREATE TABLE ONARIM("+
                "id"+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                "cihaz_id"+" INTEGER , "+
                "isyeri_id"+" INTEGER ,"+
                "onarimdurum_id"+" INTEGER ,"+
                "toplam_sure"+" INTEGER ," +
                "toplam_maliyet"+" REAL ,"+
                "baslama_tarihi"+" DATE ,"+
                "bitis_tarihi"+" DATE ,"+
                "ariza_tipi_id"+" INTEGER ,"+
                "marka_id"+" INTEGER ,"+
                "model_id"+" INTEGER ,"+
                "onarim_bitti"+" INTEGER ,"+
                "aciklama"+" TEXT ,"+
                "silindi"+" INTEGER" +")";
        db.execSQL(qq6);

        String qq7="CREATE TABLE ONARIMHAREKET("+
                "id"+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                "onarim_id"+" INTEGER , "+
                "onarim_hareket_tipi_id"+" INTEGER ,"+
                "harcananyedekparca_id"+" INTEGER ,"+
                "hareket_tarihi"+" DATE ," +
                "aciklama"+" TEXT ,"+
                "parcaadet"+" INTEGER ," +
                "silindi"+" INTEGER" +")";
        db.execSQL(qq7);

        String qq8="CREATE TABLE TIPGRUPLARI("+
                "id"+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                "adi"+" TEXT , "+
                "aciklama"+" TEXT ,"+
                "silindi"+" INTEGER" +")";
        db.execSQL(qq8);

        String qq9="CREATE TABLE DINAMIK_TIPLER("+
                "id"+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                "tipgrupid"+" INTEGER , "+
                "adi"+" TEXT ,"+
                "aciklama"+" TEXT ,"+
                "silindi"+" INTEGER" +")";
        db.execSQL(qq9);

    }
}

