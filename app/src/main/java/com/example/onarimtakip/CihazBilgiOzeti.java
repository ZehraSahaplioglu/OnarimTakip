package com.example.onarimtakip;

import static android.graphics.Color.BLACK;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Cihaz;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Okuyucu;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Onarim;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.OnarimHareket;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.SabitDegiskenler;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Yazici;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CihazBilgiOzeti extends AppCompatActivity {

    Okuyucu okc;
    Yazici yzc;
    int OnarimID;
    Onarim ustOnarim;
    LinearLayout anaLayout;

    LinearLayout.LayoutParams hlayouParametreleri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cihaz_bilgi_ozeti);

        yzc=new Yazici(CihazBilgiOzeti.this);
        okc=new Okuyucu(CihazBilgiOzeti.this);

        ((TextView) findViewById(R.id.titleTextView)).setText("Cihaz Bilgi Özeti");

        //bundle diğer sayfadan veri alır.
        Bundle bndl=getIntent().getExtras();
        OnarimID=bndl.getInt("onarim_id");

        ustOnarim=okc.idyegoreOnarimver(OnarimID);

        anaLayout=(LinearLayout) findViewById(R.id.layout_bilgiozeti);

        yzc=new Yazici(CihazBilgiOzeti.this);
        okc=new Okuyucu(CihazBilgiOzeti.this);

        hareketListesigetir();

        Button yeni_tamir_Button= findViewById(R.id.yeni_tamir_Button);
        yeni_tamir_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent = new Intent(CihazBilgiOzeti.this, CihazBilgiOzeti.class);
               // intent.putExtra("onarim_id",OnarimID);
               // startActivity(intent);

                yzc.yeniTamirGirKaydet(OnarimID, SabitDegiskenler.ONRDRM_ACIK, -1,
                        "Onarım başlatıldı..");

                hareketListesigetir();

            }
        });

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public  void hareketListesigetir(){

        //layout'un içindeki edittext ve button'ların ozelliklerini burada topladık
        //agirligi fazla olan az yer kaplar
        LinearLayout.LayoutParams buttonParametreleri=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,3);
        LinearLayout.LayoutParams textviewParametreleri=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,1);

        // layout'ların ozelliklerini buradaki değişkende topladık
        hlayouParametreleri=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);


        ArrayList<OnarimHareket> onarimHareketListesi=okc.onarimIDyegoreOzetver(OnarimID);

        // kaydedilmiş ilk 30 cihaz bilgisini verir

        anaLayout.removeAllViews();
        for(int i=0; i<onarimHareketListesi.size() && i<30; i++){

            OnarimHareket curOnarimHrk=onarimHareketListesi.get(i);

            //ara layout olusturduk ve içine bilgilerin yazacağı edittext ve guncelle butonunu yazdık
            LinearLayout araLayout=new LinearLayout(CihazBilgiOzeti.this);
            araLayout.setOrientation(LinearLayout.HORIZONTAL);  //yatay layout
            araLayout.setLayoutParams(hlayouParametreleri);

            TextView ozetTextView=new TextView(CihazBilgiOzeti.this);
            ImageButton duzenleButon=new ImageButton(CihazBilgiOzeti.this);
            ImageButton silButton = new ImageButton(CihazBilgiOzeti.this);

            duzenleButon.setBackground(getDrawable(R.drawable.update_24));
            silButton.setBackground(getDrawable(R.drawable.baseline_delete));

            // görünmesini istediğimiz yazılar yazacak:

            Cihaz chz=okc.idyegoreCihazver(ustOnarim.getCihaz_id());

            String harekettarih=" ";
            Calendar calbas=null;
            Date bastar=curOnarimHrk.getHareket_tarihi();
            if(bastar!=null){
                calbas=Calendar.getInstance();
                calbas.setTime(bastar);
                int bastar_ay=calbas.get(Calendar.MONTH)+1;
                harekettarih=calbas.get(Calendar.DAY_OF_MONTH)+ "/" +bastar_ay+"/"+calbas.get(Calendar.YEAR);
            }

            if (curOnarimHrk.getOnarim_hareket_tipi_id()== SabitDegiskenler.ONR_ACIKLAMA){

                ozetTextView.setText(chz.getEnvno()+" Açıklama --> "+curOnarimHrk.getAciklama()+ " "+ harekettarih);
            }
            else if (curOnarimHrk.getOnarim_hareket_tipi_id()==SabitDegiskenler.ONR_DURDUR){

                ozetTextView.setText(chz.getEnvno()+" Durduruldu --> "+curOnarimHrk.getAciklama()+ " "+ harekettarih);

            }
            else if (curOnarimHrk.getOnarim_hareket_tipi_id()==SabitDegiskenler.ONR_BASLAT){

                ozetTextView.setText(chz.getEnvno()+" Başlatıldı --> "+curOnarimHrk.getAciklama()+ " "+ harekettarih);

            }
            else if (curOnarimHrk.getOnarim_hareket_tipi_id()==SabitDegiskenler.ONR_PARCADEGISIMI) {

                String yedekParca=okc.idyegoretipver(curOnarimHrk.getHarcananyedekparca_id()).getAdi();

                ozetTextView.setText(chz.getEnvno()+ " Parça Değişimi --> "+curOnarimHrk.getAciklama()+ " "+ curOnarimHrk.getParcaadet()+
                        " adet "+yedekParca+ " "+" "+ harekettarih);

            }
            else if (curOnarimHrk.getOnarim_hareket_tipi_id()==SabitDegiskenler.ONR_SONLANDIR) {

                ozetTextView.setText(chz.getEnvno()+" Sonlandırıldı --> "+ curOnarimHrk.getAciklama()+ " "+ harekettarih);

            }


            duzenleButon.setLayoutParams(buttonParametreleri);
            silButton.setLayoutParams(buttonParametreleri);
            ozetTextView.setLayoutParams(textviewParametreleri);
            ozetTextView.setTextSize(15);
            ozetTextView.setTextColor(BLACK);

            //sayfamıza layout ve içindeki edittext ile button'u ekliyoruz
            araLayout.addView(ozetTextView);
            araLayout.addView(duzenleButon);
            araLayout.addView(silButton);
            anaLayout.addView(araLayout);

            duzenleButon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CihazBilgiOzeti.this, YeniTamirGir.class);
                    intent.putExtra("onarimhrk_id", curOnarimHrk.getId());
                    startActivity(intent);

                }
            });

            silButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder silmeDialog = new AlertDialog.Builder(CihazBilgiOzeti.this);
                    silmeDialog.setMessage("Silmek istediğinizden emin misiniz?")
                            .setTitle("Silinecek!")
                            .setCancelable(false)
                            .setIcon(R.drawable.warning_24)
                            .setPositiveButton("Evet", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    //Silme işlemi bu kısma kodlanacak
                                    yzc.onarimHareketSil(curOnarimHrk.getId());
                                    Toast.makeText(CihazBilgiOzeti.this, "Silme İşlemi Başarılı!", Toast.LENGTH_SHORT).show();
                                    hareketListesigetir();
                                }
                            })
                            .setNegativeButton("Hayır", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();

                }
            });

        }

    }

}