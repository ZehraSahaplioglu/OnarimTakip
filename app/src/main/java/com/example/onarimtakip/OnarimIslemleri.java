package com.example.onarimtakip;

import static android.graphics.Color.BLACK;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onarimtakip.Tanimlamalar.Fonksiyonlar;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Cihaz;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Okuyucu;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Onarim;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.SabitDegiskenler;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Yazici;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class OnarimIslemleri extends AppCompatActivity {

    Okuyucu okc;
    Yazici yzc;
    Fonksiyonlar fnk;
    Onarim curOnarim;

    LinearLayout anaLayout;
    LinearLayout.LayoutParams olayouParametreleri;
    ImageButton yeni_onarim_Button;
    ImageButton ara_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onarim_islemleri);

        yzc=new Yazici(OnarimIslemleri.this);
        okc=new Okuyucu(OnarimIslemleri.this);
        fnk=new Fonksiyonlar(OnarimIslemleri.this);

        ((TextView) findViewById(R.id.titleTextView)).setText("Onarım İşlemleri Sayfası");

        // Burada kendimiz layout oluşturup içine edittext ve button ekliyoruz
        // edittext'lerde kaydedilmis cihazlarin bilgileri yazar
        // button'larda ise güncelle yazar ve tıklandığında GuncelleKayıt sayfasına yönlendirir

        anaLayout=(LinearLayout) findViewById(R.id.layout_onarim);

        yeni_onarim_Button = findViewById(R.id.yeni_onarim_Button);
        yeni_onarim_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String envText= ((EditText)findViewById(R.id.onarim_islemleri_edit)).getText().toString();
                Cihaz chz=okc.envgoreCihazver(envText);

                if (chz!= null && okc.acikOnarimvarmi(chz.getId())) {

                    Toast.makeText(OnarimIslemleri.this, "Aynı anda 2 açık onarım olamaz!", Toast.LENGTH_SHORT).show();

                }else {

                    yzc.onarimKaydet(chz.getId(),chz.getIsyeri_id(), SabitDegiskenler.ONRDRM_ACIK,0,0,-1,
                            chz.getMarka_id(), chz.getModel_id(),0,"");

                    onarimListesiGetir();
                }

            }
        });

        ara_Button = findViewById(R.id.ara_Button);
        ara_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String envText= ((EditText)findViewById(R.id.onarim_islemleri_edit)).getText().toString();
                Cihaz chz=okc.envgoreCihazver(envText);

                onarimListesiGetir();

            }
        });




    }
    public  void onarimListesiGetir(){

        olayouParametreleri=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        //layout'un içindeki edittext ve button'ların ozelliklerini burada topladık
        //agirligi fazla olan az yer kaplar
        LinearLayout.LayoutParams buttonParametreleri=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,3);
        LinearLayout.LayoutParams textviewParametreleri=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,1);

        String envText= ((EditText)findViewById(R.id.onarim_islemleri_edit)).getText().toString();
        Cihaz chz=okc.envgoreCihazver(envText);

        ArrayList<Onarim> onarimlistesi=okc.cihazIDyegoreOnarimver(chz.getId());

        // kaydedilmiş ilk 30 cihaz bilgisini verir

        anaLayout.removeAllViews();  //layoutu yeniler
        for(int i=0; i<onarimlistesi.size() && i<30; i++){

            curOnarim=onarimlistesi.get(i);

            //ara layout olusturduk ve içine bilgilerin yazacağı edittext ve guncelle butonunu yazdık
            LinearLayout araLayout=new LinearLayout(OnarimIslemleri.this);
            araLayout.setOrientation(LinearLayout.HORIZONTAL);  //yatay layout
            araLayout.setLayoutParams(olayouParametreleri);
            araLayout.setPadding(0,10, 0, 40);

            TextView ozetTextView=new TextView(OnarimIslemleri.this);
            ozetTextView.setLayoutParams(textviewParametreleri);

            ImageButton onarButon=new ImageButton(OnarimIslemleri.this);
            onarButon.setLayoutParams(buttonParametreleri);
            onarButon.setBackgroundResource(R.drawable.baseline_onar);

            ImageButton silButon=new ImageButton(OnarimIslemleri.this);
            silButon.setLayoutParams(buttonParametreleri);
            silButon.setBackgroundResource(R.drawable.baseline_delete);

            if (curOnarim.getOnarimdurum_id()==SabitDegiskenler.ONRDRM_ACIK) {

                araLayout.setBackgroundColor(getColor(R.color.mavi));

            } else if (curOnarim.getOnarimdurum_id()==SabitDegiskenler.ONRDRM_YENIDEN_BASLATILDI) {

                araLayout.setBackgroundColor(getColor(R.color.mavi1));

            } else if (curOnarim.getOnarimdurum_id()==SabitDegiskenler.ONRDRM_DURDURULDU){

                araLayout.setBackgroundColor(getColor(R.color.mavi2));

            }
            else if (curOnarim.getOnarimdurum_id()==SabitDegiskenler.ONRDRM_SONLANDIRILDI) {

                araLayout.setBackgroundColor(getColor(R.color.mavi22));

            }


            String bastartext=" ";
            Calendar calbas=null;
            Date bastar=curOnarim.getBaslama_tarihi();
            if(bastar!=null){
                calbas=Calendar.getInstance();
                calbas.setTime(bastar);
                int bastar_ay=calbas.get(Calendar.MONTH)+1;
                bastartext=calbas.get(Calendar.DAY_OF_MONTH)+ "/" +bastar_ay+"/"+calbas.get(Calendar.YEAR);
            }

            String bitirtartext=" ";
            Calendar calbit=null;
            Date bitirtar=curOnarim.getBitis_tarihi();
            if(bitirtar!=null){
                calbit=Calendar.getInstance();
                calbit.setTime(bitirtar);
                int bitirtar_ay=calbit.get(Calendar.MONTH)+1;
                bitirtartext=calbit.get(Calendar.DAY_OF_MONTH)+"/"+bitirtar_ay+"/"+calbit.get(Calendar.YEAR);
            }
            String toplamSure=fnk.onarimSureHesapla(curOnarim.getId()).getText();

            ozetTextView.setText(chz.getEnvno()+" "+okc.idyegoreisyeriver(curOnarim.getIsyeri_id()).getIsyeri_adi()+" "+
                    okc.idyegoremarkaver(curOnarim.getMarka_id()).getMarka_adi()+ " "+
                    okc.idyegoremodelver(curOnarim.getModel_id()).getModel_adi()+" "+bastartext+ " "+ bitirtartext
            + " maliyet: " +curOnarim.getToplam_maliyet() + " toplam süre: " + toplamSure);

            ozetTextView.setTextSize(15);
            ozetTextView.setTextColor(BLACK);
            ozetTextView.setPadding(5,0,5,50);

            //sayfamıza layout ve içindeki edittext ile button'u ekliyoruz
            araLayout.addView(ozetTextView);
            araLayout.addView(onarButon);
            araLayout.addView(silButon);
            anaLayout.addView(araLayout);

            onarButon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(OnarimIslemleri.this, CihazBilgiOzeti.class);
                    intent.putExtra("onarim_id", curOnarim.getId());
                    startActivity(intent);

                }
            });

            silButon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder silmeDialog = new AlertDialog.Builder(OnarimIslemleri.this);
                    silmeDialog.setMessage("Onarımı silmek istediğinizden emin misiniz?")
                            .setTitle("Onarım silinecek!")
                            .setCancelable(false)
                            .setIcon(R.drawable.warning_24)
                            .setPositiveButton("Evet", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    //Silme işlemi bu kısma kodlanacak
                                    yzc.onarimSil(curOnarim.getId());
                                    Toast.makeText(OnarimIslemleri.this, "Silme İşlemi Başarılı!", Toast.LENGTH_SHORT).show();
                                    onarimListesiGetir();
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