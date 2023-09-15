package com.example.onarimtakip;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onarimtakip.Tanimlamalar.DegerIkilisi;
import com.example.onarimtakip.Tanimlamalar.DropDownAdapter;
import com.example.onarimtakip.Tanimlamalar.Fonksiyonlar;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Okuyucu;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Onarim;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.OnarimHareket;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.SabitDegiskenler;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Yazici;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.YedekParca;

import java.util.ArrayList;

public class YeniTamirGir extends AppCompatActivity {
    Onarim ustOnarim;
    int oncekiParca_id,oncekiParcaAdet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeni_tamir_gir);

        ((TextView) findViewById(R.id.titleTextView)).setText("Yeni Tamir Sayfası");

        Fonksiyonlar fnk= new Fonksiyonlar(YeniTamirGir.this);
        Okuyucu okc = new Okuyucu(YeniTamirGir.this);
        Yazici yzc = new Yazici(YeniTamirGir.this);

        Bundle bndl=getIntent().getExtras();
        int OnarimHrkID=bndl.getInt("onarimhrk_id");

        OnarimHareket curOnarimHrkt=okc.onarimHareketIDyegoreHareketVer(OnarimHrkID);


        if(curOnarimHrkt.getOnarim_hareket_tipi_id()==SabitDegiskenler.ONR_PARCADEGISIMI){
            oncekiParca_id=curOnarimHrkt.getHarcananyedekparca_id();
            oncekiParcaAdet=curOnarimHrkt.getParcaadet();
        }

        ustOnarim=okc.idyegoreOnarimver(curOnarimHrkt.getOnarim_id());

        //ArrayList<OnarimHareket> guncellenecekOnarimHareket=okc.onarimHareketIDyegoreOzetver(OnarimID);

        ArrayList<DegerIkilisi> harketTipleri=new ArrayList<>();
        harketTipleri.add(new DegerIkilisi(SabitDegiskenler.ONR_ACIKLAMA,"Açıklama"));
        harketTipleri.add(new DegerIkilisi(SabitDegiskenler.ONR_BASLAT,"Başlat"));
        harketTipleri.add(new DegerIkilisi(SabitDegiskenler.ONR_PARCADEGISIMI,"Parça Değişimi"));
        harketTipleri.add(new DegerIkilisi(SabitDegiskenler.ONR_DURDUR,"Durdur"));
        harketTipleri.add(new DegerIkilisi(SabitDegiskenler.ONR_SONLANDIR,"Sonlandır"));


        // Layout Tanımlamalar
        LinearLayout yedekparcal= ((LinearLayout) findViewById(R.id.layout_yedekparca));
        LinearLayout arizal= ((LinearLayout) findViewById(R.id.layout_ariza));
        LinearLayout guncellel= ((LinearLayout) findViewById(R.id.guncelle_layout));
        LinearLayout onarimbitirl= ((LinearLayout) findViewById(R.id.onarimbitir_layout));


        Spinner mevcutHrkTipSpinner=findViewById(R.id.spn_harekettip);
        DropDownAdapter hareketadapter=new DropDownAdapter(this,harketTipleri);
        mevcutHrkTipSpinner.setAdapter(hareketadapter);

        Spinner mevcutYedekParcaSpinner=((Spinner) findViewById(R.id.spn_yedekparca));
        DropDownAdapter yedekparcaadapter=new DropDownAdapter(this,okc.tipgrubunaGoreTipleriver(SabitDegiskenler.YEDEK_PARCA_TIP));
        mevcutYedekParcaSpinner.setAdapter(yedekparcaadapter);

        Spinner mevcutParcaSpinner=findViewById(R.id.spn_parca);
        DropDownAdapter parcaadapter=new DropDownAdapter(this,okc.tipgrubunaGoreTipleriver(SabitDegiskenler.YEDEK_PARCA_TIP));
        mevcutParcaSpinner.setAdapter(parcaadapter);

        mevcutHrkTipSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int secilenHrkTipId=((DegerIkilisi) mevcutHrkTipSpinner.getSelectedItem()).getId();

                if(secilenHrkTipId!=-1) {

                    DropDownAdapter hrkparcaadapter=new DropDownAdapter(YeniTamirGir.this,
                            okc.tipegoreYedekParcaListever(secilenHrkTipId));

                    mevcutYedekParcaSpinner.setAdapter(hrkparcaadapter);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mevcutYedekParcaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int secilenTipId=((DegerIkilisi) mevcutYedekParcaSpinner.getSelectedItem()).getId();

                if(secilenTipId!=-1) {

                    DropDownAdapter yedekparcaadapter=new DropDownAdapter(YeniTamirGir.this,
                            okc.tipegoreYedekParcaListever(secilenTipId));

                    mevcutParcaSpinner.setAdapter(yedekparcaadapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Spinner arizaSpinner=findViewById(R.id.spn_arizatip);
        DropDownAdapter arizaadapter=new DropDownAdapter(this,okc.tipgrubunaGoreTipleriver(SabitDegiskenler.ARIZA_TIP));
        arizaSpinner.setAdapter(arizaadapter);


        //ilk veri doldurma işlemi

        if(curOnarimHrkt.getOnarim_hareket_tipi_id()==SabitDegiskenler.ONR_PARCADEGISIMI) {

            yedekparcal.setVisibility(View.VISIBLE); //görünür oldu
            arizal.setVisibility(View.INVISIBLE);  //görünmez oldu
            guncellel.setVisibility(View.VISIBLE);
            onarimbitirl.setVisibility(View.INVISIBLE);

            YedekParca ydkprc=okc.idyegoreYedekParcaver(curOnarimHrkt.getHarcananyedekparca_id());

            fnk.spinnerSelector(mevcutHrkTipSpinner,curOnarimHrkt.getOnarim_hareket_tipi_id());
            fnk.spinnerSelector(mevcutYedekParcaSpinner,ydkprc.getYedekparcatipi_id());
            fnk.spinnerSelector(mevcutParcaSpinner,curOnarimHrkt.getHarcananyedekparca_id());

            ((EditText) findViewById(R.id.hareketaciklama_edit)).setText(curOnarimHrkt.getAciklama());
            ((EditText) findViewById(R.id.yedekadetNumber)).setText(String.valueOf(curOnarimHrkt.getParcaadet()));
        }
        else if (curOnarimHrkt.getOnarim_hareket_tipi_id()==SabitDegiskenler.ONR_ACIKLAMA) {

            yedekparcal.setVisibility(View.INVISIBLE);
            arizal.setVisibility(View.INVISIBLE);
            guncellel.setVisibility(View.VISIBLE);
            onarimbitirl.setVisibility(View.INVISIBLE);

            ((EditText) findViewById(R.id.hareketaciklama_edit)).setText(curOnarimHrkt.getAciklama());

        }
        else if (curOnarimHrkt.getOnarim_hareket_tipi_id()==SabitDegiskenler.ONR_DURDUR) {

            yedekparcal.setVisibility(View.INVISIBLE);
            arizal.setVisibility(View.INVISIBLE);
            guncellel.setVisibility(View.VISIBLE);
            onarimbitirl.setVisibility(View.INVISIBLE);

            fnk.spinnerSelector(mevcutHrkTipSpinner,curOnarimHrkt.getOnarim_hareket_tipi_id());
            ((EditText) findViewById(R.id.hareketaciklama_edit)).setText(curOnarimHrkt.getAciklama());

        }
        else if (curOnarimHrkt.getOnarim_hareket_tipi_id()==SabitDegiskenler.ONR_BASLAT) {

            yedekparcal.setVisibility(View.INVISIBLE);
            arizal.setVisibility(View.INVISIBLE);
            guncellel.setVisibility(View.VISIBLE);
            onarimbitirl.setVisibility(View.INVISIBLE);

            fnk.spinnerSelector(mevcutHrkTipSpinner,curOnarimHrkt.getOnarim_hareket_tipi_id());
            ((EditText) findViewById(R.id.hareketaciklama_edit)).setText(curOnarimHrkt.getAciklama());

        }
        else if (curOnarimHrkt.getOnarim_hareket_tipi_id()==SabitDegiskenler.ONR_SONLANDIR) {

            yedekparcal.setVisibility(View.INVISIBLE);
            arizal.setVisibility(View.VISIBLE);
            guncellel.setVisibility(View.INVISIBLE);
            onarimbitirl.setVisibility(View.VISIBLE);

            fnk.spinnerSelector(mevcutHrkTipSpinner,curOnarimHrkt.getOnarim_hareket_tipi_id());
            fnk.spinnerSelector(arizaSpinner,ustOnarim.getAriza_tipi_id());

            ((EditText) findViewById(R.id.hareketaciklama_edit)).setText(curOnarimHrkt.getAciklama());

        }



        mevcutHrkTipSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int secilenOnarimHarekettipi= ((DegerIkilisi)mevcutHrkTipSpinner.getSelectedItem()).getId();

                if(secilenOnarimHarekettipi==SabitDegiskenler.ONR_PARCADEGISIMI){

                    yedekparcal.setVisibility(View.VISIBLE); //görünür oldu
                    arizal.setVisibility(View.INVISIBLE);  //görünmez oldu
                    guncellel.setVisibility(View.VISIBLE);
                    onarimbitirl.setVisibility(View.INVISIBLE);

                    ((Button)findViewById(R.id.guncelleButton)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (ustOnarim.getOnarimdurum_id() != SabitDegiskenler.ONRDRM_DURDURULDU) {
                                String aciklama;
                                aciklama=((EditText) findViewById(R.id.hareketaciklama_edit)).getText().toString();
                                int adet =Integer.parseInt(((EditText) findViewById(R.id.yedekadetNumber)).getText().toString());
                                int yedekparcaID= ((DegerIkilisi)mevcutParcaSpinner.getSelectedItem()).getId();
                                int mevcutStokMiktar=okc.idyegoreYedekParcaver(yedekparcaID).getStok_miktar();

                                yzc.yeniTamirGirGuncelle(curOnarimHrkt.getOnarim_id(), SabitDegiskenler.ONR_PARCADEGISIMI, yedekparcaID,
                                        aciklama, OnarimHrkID,adet);

                                yzc.yedekParcaStokGuncelle(yedekparcaID,mevcutStokMiktar-adet);

                                if(curOnarimHrkt.getOnarim_hareket_tipi_id()==SabitDegiskenler.ONR_PARCADEGISIMI){
                                    yzc.yedekParcaStokGuncelle(oncekiParca_id,
                                            okc.idyegoreYedekParcaver(oncekiParca_id).getStok_miktar() +oncekiParcaAdet);
                                }

                                Toast.makeText(YeniTamirGir.this, "Güncelleme Başarılı.", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(YeniTamirGir.this, CihazBilgiOzeti.class);
                                intent.putExtra("onarim_id",curOnarimHrkt.getOnarim_id());
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(YeniTamirGir.this, "Önce onarımı başlatınız !!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else if (secilenOnarimHarekettipi==SabitDegiskenler.ONR_ACIKLAMA) {

                    yedekparcal.setVisibility(View.INVISIBLE);
                    arizal.setVisibility(View.INVISIBLE);
                    guncellel.setVisibility(View.VISIBLE);
                    onarimbitirl.setVisibility(View.INVISIBLE);

                    ((Button)findViewById(R.id.guncelleButton)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (ustOnarim.getOnarimdurum_id() != SabitDegiskenler.ONRDRM_DURDURULDU) {
                                String aciklama;
                                aciklama = ((EditText) findViewById(R.id.hareketaciklama_edit)).getText().toString();

                                yzc.yeniTamirGirGuncelle(curOnarimHrkt.getOnarim_id(), SabitDegiskenler.ONR_ACIKLAMA, -1,
                                        aciklama, OnarimHrkID, 0);

                                if (curOnarimHrkt.getOnarim_hareket_tipi_id() == SabitDegiskenler.ONR_PARCADEGISIMI) {
                                    yzc.yedekParcaStokGuncelle(oncekiParca_id,
                                            okc.idyegoreYedekParcaver(oncekiParca_id).getStok_miktar() + oncekiParcaAdet);
                                }

                                Toast.makeText(YeniTamirGir.this, "Güncelleme Başarılı.", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(YeniTamirGir.this, CihazBilgiOzeti.class);
                                intent.putExtra("onarim_id", curOnarimHrkt.getOnarim_id());
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(YeniTamirGir.this, "Önce onarımı başlatınız !!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }else if (secilenOnarimHarekettipi==SabitDegiskenler.ONR_DURDUR) {

                    yedekparcal.setVisibility(View.INVISIBLE);
                    arizal.setVisibility(View.INVISIBLE);
                    guncellel.setVisibility(View.VISIBLE);
                    onarimbitirl.setVisibility(View.INVISIBLE);

                    ((Button)findViewById(R.id.guncelleButton)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (ustOnarim.getOnarimdurum_id() != SabitDegiskenler.ONRDRM_DURDURULDU) {
                                String aciklama;
                                aciklama = ((EditText) findViewById(R.id.hareketaciklama_edit)).getText().toString();

                                yzc.yeniTamirGirGuncelle(curOnarimHrkt.getOnarim_id(), SabitDegiskenler.ONR_DURDUR, -1,
                                        aciklama, OnarimHrkID, 0);

                                yzc.onarimSonlandir(SabitDegiskenler.ONRDRM_DURDURULDU, 0, 0, -1, 0,
                                        aciklama, curOnarimHrkt.getOnarim_id());


                                if (curOnarimHrkt.getOnarim_hareket_tipi_id() == SabitDegiskenler.ONR_PARCADEGISIMI) {
                                    yzc.yedekParcaStokGuncelle(oncekiParca_id,
                                            okc.idyegoreYedekParcaver(oncekiParca_id).getStok_miktar() + oncekiParcaAdet);
                                }

                                Toast.makeText(YeniTamirGir.this, "Güncelleme Başarılı.", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(YeniTamirGir.this, CihazBilgiOzeti.class);
                                intent.putExtra("onarim_id", curOnarimHrkt.getOnarim_id());
                                startActivity(intent);

                            }
                            else{
                                Toast.makeText(YeniTamirGir.this, "Onarım zaten durdurulmuş !!", Toast.LENGTH_SHORT).show();
                            }
                        }

                    });

                }else if (secilenOnarimHarekettipi==SabitDegiskenler.ONR_BASLAT) {

                    yedekparcal.setVisibility(View.INVISIBLE);
                    arizal.setVisibility(View.INVISIBLE);
                    guncellel.setVisibility(View.VISIBLE);
                    onarimbitirl.setVisibility(View.INVISIBLE);

                    ((Button)findViewById(R.id.guncelleButton)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String aciklama;
                            aciklama=((EditText) findViewById(R.id.hareketaciklama_edit)).getText().toString();

                            yzc.yeniTamirGirGuncelle(curOnarimHrkt.getOnarim_id(), SabitDegiskenler.ONR_BASLAT, -1,
                                    aciklama, OnarimHrkID, 0);

                            yzc.onarimSonlandir(SabitDegiskenler.ONRDRM_YENIDEN_BASLATILDI,0,0,-1,0,
                                    aciklama, curOnarimHrkt.getOnarim_id());

                            if(curOnarimHrkt.getOnarim_hareket_tipi_id()==SabitDegiskenler.ONR_PARCADEGISIMI){
                                yzc.yedekParcaStokGuncelle(oncekiParca_id,
                                        okc.idyegoreYedekParcaver(oncekiParca_id).getStok_miktar()+oncekiParcaAdet);
                            }

                            Toast.makeText(YeniTamirGir.this, "Güncelleme Başarılı.", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(YeniTamirGir.this, CihazBilgiOzeti.class);
                            intent.putExtra("onarim_id",curOnarimHrkt.getOnarim_id());
                            startActivity(intent);

                        }
                    });

                }else if (secilenOnarimHarekettipi==SabitDegiskenler.ONR_SONLANDIR) {

                    yedekparcal.setVisibility(View.INVISIBLE);
                    arizal.setVisibility(View.VISIBLE);
                    guncellel.setVisibility(View.INVISIBLE);
                    onarimbitirl.setVisibility(View.VISIBLE);

                    ((Button) findViewById(R.id.onarimbitirButton)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String aciklama;
                            aciklama=((EditText) findViewById(R.id.hareketaciklama_edit)).getText().toString();
                            int arizaID= ((DegerIkilisi)arizaSpinner.getSelectedItem()).getId();

                            yzc.yeniTamirGirGuncelle(curOnarimHrkt.getOnarim_id(), SabitDegiskenler.ONR_SONLANDIR,-1,aciklama,
                                    OnarimHrkID,0);

                            yzc.onarimSonlandir(SabitDegiskenler.ONRDRM_SONLANDIRILDI,fnk.onarimSureHesapla(ustOnarim.getId()).getId(),
                                    fnk.maliyetHesapla(ustOnarim.getId()),arizaID,1,aciklama,
                                    curOnarimHrkt.getOnarim_id());

                            if(curOnarimHrkt.getOnarim_hareket_tipi_id()==SabitDegiskenler.ONR_PARCADEGISIMI){
                                yzc.yedekParcaStokGuncelle(oncekiParca_id,
                                        okc.idyegoreYedekParcaver(oncekiParca_id).getStok_miktar()+oncekiParcaAdet);
                            }

                            Toast.makeText(YeniTamirGir.this, "Sonlandırma işlemi başarılı...", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(YeniTamirGir.this, CihazBilgiOzeti.class);
                            intent.putExtra("onarim_id",curOnarimHrkt.getOnarim_id());
                            startActivity(intent);

                        }
                    });

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        ImageButton yedekparcaButton = findViewById(R.id.yedekparcaButton);
        yedekparcaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YeniTamirGir.this, TipTanimlama.class);
                intent.putExtra("tiptipi", SabitDegiskenler.YEDEK_PARCA_TIP);
                startActivity(intent);
            }
        });

        ImageButton arizaButton = findViewById(R.id.arizaButton);
        arizaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YeniTamirGir.this, TipTanimlama.class);
                intent.putExtra("tiptipi", SabitDegiskenler.ARIZA_TIP);
                startActivity(intent);
            }
        });


    }
}