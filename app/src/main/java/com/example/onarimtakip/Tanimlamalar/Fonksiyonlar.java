package com.example.onarimtakip.Tanimlamalar;

import android.content.Context;
import android.widget.Spinner;

import com.example.onarimtakip.Tanimlamalar.VeriTabani.Okuyucu;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Onarim;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.OnarimHareket;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.SabitDegiskenler;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Yazici;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.YedekParca;

import java.util.ArrayList;
import java.util.Date;

public class Fonksiyonlar {
Okuyucu okc;


    public Fonksiyonlar(Context cntx) {

        okc=new Okuyucu(cntx);
    }

    public void spinnerSelector(Spinner spnr, int id){
        DropDownAdapter adapter= (DropDownAdapter) spnr.getAdapter();
        for(int i=0;i<adapter.getCount();i++){
            if(((DegerIkilisi) adapter.getItem(i)).getId()==id){
                spnr.setSelection(i);
                return;
            }
        }
    }

    OnarimHareket curOnarimHrkt;
    Onarim curOnarim;

    public double maliyetHesapla(int onarimId){
        double sonuc=0;

        ArrayList<OnarimHareket> tamirlistesi=okc.onarimIDyegoreOzetver(onarimId);

            for (int i=0;i< tamirlistesi.size(); i++){

                curOnarimHrkt=tamirlistesi.get(i);

                if(curOnarimHrkt.getOnarim_hareket_tipi_id()==SabitDegiskenler.ONR_PARCADEGISIMI){

                    YedekParca ydkprc=okc.idyegoreYedekParcaver( curOnarimHrkt.getHarcananyedekparca_id());
                    sonuc=sonuc+(ydkprc.getFiyat() * curOnarimHrkt.getParcaadet());
                }

            }


        return  sonuc;
    }

    public String gunFarkiVer(Date date1, Date date2){
        String fark = "";
        if(date1!=null && date2!=null) {

            int gun = 0;
            int saat = 0;
            int dakika = 0;
            long diff = date2.getTime() - date1.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);
            gun = (int) diffDays;
            saat = (int) (diff - (gun * 24 * 60 * 60 * 1000)) / (60 * 60 * 1000);
            dakika = (int) (diff - (gun * 24 * 60 * 60 * 1000 + (saat * 60 * 60 * 1000))) / (60 * 1000);
            fark = gun + " Gün " + saat + " saat " + dakika + " dakika";
        }
            return fark;
    }
    public DegerIkilisi onarimSureHesapla(int onarimId){
        String text="";
        int toplamGun=0;
        int toplamSaat=0;
        int toplamDakika=0;

        ArrayList<TarihIkilisi> tarihikiliListe=new ArrayList<>();

        Onarim ustonarim=okc.idyegoreOnarimver(onarimId);
        if(ustonarim.getOnarim_bitti()==1) {//sonlanldı
            ArrayList<OnarimHareket> onarimHareketListesi = okc.onarimIDyegoreOzetver(onarimId);
            OnarimHareket curOnarimHAreket;
            Date ilkbaslangicTar=onarimHareketListesi.get(0).getHareket_tarihi();

            for (int i = 0; i < onarimHareketListesi.size(); i++) {
                curOnarimHAreket = onarimHareketListesi.get(i);
                if(curOnarimHAreket.getOnarim_hareket_tipi_id()==SabitDegiskenler.ONR_SONLANDIR || curOnarimHAreket.getOnarim_hareket_tipi_id()==SabitDegiskenler.ONR_DURDUR) {  //sabit değişken kontrol edilecek

                    TarihIkilisi curTarihİkilisi=new TarihIkilisi();
                    curTarihİkilisi.setBasTar(ilkbaslangicTar);
                    curTarihİkilisi.setBitTar(curOnarimHAreket.getHareket_tarihi());
                    tarihikiliListe.add(curTarihİkilisi);
                }
                if(curOnarimHAreket.getOnarim_hareket_tipi_id()==SabitDegiskenler.ONR_BASLAT){
                    ilkbaslangicTar=curOnarimHAreket.getHareket_tarihi();
                }

            }

            int artanSaat=0;
            int artanGun=0;

            for (int i=0;i<tarihikiliListe.size();i++){
                GunSaatDkk curucluzaman=gunFarki3Ver(tarihikiliListe.get(i).basTar,tarihikiliListe.get(i).bitTar);
                toplamGun=toplamGun+curucluzaman.getGun();
                toplamSaat=toplamSaat+curucluzaman.getSaat();
                toplamDakika=toplamDakika+curucluzaman.getDakika();
            }
            if(toplamDakika>59){
                artanSaat=toplamDakika/60;
                toplamDakika=toplamDakika%60;
                toplamSaat=toplamSaat+artanSaat;
            }
            if(toplamSaat>23){
                artanGun=toplamSaat/24;
                toplamSaat=toplamSaat%24;
                toplamGun=toplamGun+artanGun;
            }
            text=toplamGun + " Gün " + toplamSaat + " saat " + toplamDakika + " dakika";


        }
        DegerIkilisi sonuc=new DegerIkilisi(toplamDakika+(toplamSaat*60)+(toplamGun*24*60),text);
        return sonuc;
    }
    public GunSaatDkk gunFarki3Ver(Date date1, Date date2){
        GunSaatDkk sonuc=new GunSaatDkk();

        if(date1!=null && date2!=null) {

            int gun = 0;
            int saat = 0;
            int dakika = 0;
            long diff = date2.getTime() - date1.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);
            gun = (int) diffDays;
            saat = (int) (diff - (gun * 24 * 60 * 60 * 1000)) / (60 * 60 * 1000);
            dakika = (int) (diff - (gun * 24 * 60 * 60 * 1000 + (saat * 60 * 60 * 1000))) / (60 * 1000);

            sonuc.setGun(gun);
            sonuc.setSaat(saat);
            sonuc.setDakika(dakika);
        }
        return sonuc;
    }
}
