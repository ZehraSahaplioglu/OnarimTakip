package com.example.onarimtakip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onarimtakip.Tanimlamalar.DegerIkilisi;
import com.example.onarimtakip.Tanimlamalar.DropDownAdapter;
import com.example.onarimtakip.Tanimlamalar.Fonksiyonlar;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Model;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Okuyucu;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.SabitDegiskenler;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Yazici;

public class YeniCihazKayit extends AppCompatActivity {
    Okuyucu okc;
    Yazici yzc;
    Fonksiyonlar fnk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeni_cihaz_kayit);

        ((TextView) findViewById(R.id.titleTextView)).setText("Yeni Cihaz Kayıt Sayfası");

        yzc= new Yazici(YeniCihazKayit.this);
        okc=new Okuyucu(YeniCihazKayit.this);
        fnk = new Fonksiyonlar(YeniCihazKayit.this);


        //MARKA TANIMLAMA
        Spinner mevcutMarkaSpinner=findViewById(R.id.spn_markaid);
        DropDownAdapter markaAdapter=new DropDownAdapter(this,okc.markaver());
        mevcutMarkaSpinner.setAdapter(markaAdapter);

        ImageButton marka_imageButton = findViewById(R.id.marka_imageButton);
        marka_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YeniCihazKayit.this, MarkaTanimlama.class);
                startActivity(intent);
            }
        });

        //MODEL TANIMLAMA

        Spinner mevcutModelSpinner=findViewById(R.id.spn_modelid);

        ImageButton model_imageBtn = findViewById(R.id.model_imageBtn);
        model_imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YeniCihazKayit.this, ModelTanimlama.class);
                startActivity(intent);
            }
        });

        mevcutMarkaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int secilenMarkaID = ((DegerIkilisi) mevcutMarkaSpinner.getSelectedItem()).getId();
                if (secilenMarkaID != -1){

                    DropDownAdapter modeladapter=new DropDownAdapter(YeniCihazKayit.this,
                            okc.markaIdyegoreModelListever(secilenMarkaID));

                    mevcutModelSpinner.setAdapter(modeladapter);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mevcutModelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int secilenModelId=((DegerIkilisi) mevcutModelSpinner.getSelectedItem()).getId();

                if(secilenModelId != -1) {

                    Model secilenModel = okc.idyegoremodelver(secilenModelId);
                    fnk.spinnerSelector(mevcutMarkaSpinner, secilenModel.getMarka_id());

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //İŞYERİ İŞLEMLERİ

        Spinner mevcutIsyeriSpinner=findViewById(R.id.spn_isyeriid);
        DropDownAdapter isyeriAdapter=new DropDownAdapter(this,okc.isyerleriver());
        mevcutIsyeriSpinner.setAdapter(isyeriAdapter);

        ImageButton isyeri_imageBtn = findViewById(R.id.isyeri_imageBtn);
        isyeri_imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YeniCihazKayit.this, IsyeriIslemleri.class);
                startActivity(intent);
            }
        });

        //ANDROİD SÜRÜM

        Spinner mevcutAndroidSurumSpinner=findViewById(R.id.spn_surumid);
        DropDownAdapter androidAdapter=new DropDownAdapter(this,okc.tipgrubunaGoreTipleriver(SabitDegiskenler.ANDROID_SURUM_TIP));
        mevcutAndroidSurumSpinner.setAdapter(androidAdapter);

        ImageButton andsurum_imageBtn = findViewById(R.id.andsurum_imageBtn);
        andsurum_imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YeniCihazKayit.this, TipTanimlama.class);
                intent.putExtra("tiptipi", SabitDegiskenler.ANDROID_SURUM_TIP);
                startActivity(intent);
            }
        });

        //EKRAN BOYUTU

        Spinner mevcutEkranSpinner=findViewById(R.id.spn_ekkranid);
        DropDownAdapter ekranAdapter=new DropDownAdapter(this,okc.tipgrubunaGoreTipleriver(SabitDegiskenler.EKRAN_BOYUT_TIP));
        mevcutEkranSpinner.setAdapter(ekranAdapter);

        ImageButton ekran_imageBtn = findViewById(R.id.ekran_imageBtn);
        ekran_imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YeniCihazKayit.this, TipTanimlama.class);
                intent.putExtra("tiptipi", SabitDegiskenler.EKRAN_BOYUT_TIP);
                startActivity(intent);
            }
        });



        ((Button)findViewById(R.id.kaydet_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String envnoText=  ((EditText)findViewById(R.id.envno_edit)).getText().toString();
                String gsmnoText=  ((EditText)findViewById(R.id.gsmno_edit)).getText().toString();
                String imeinoText=  ((EditText)findViewById(R.id.imeino_edit)).getText().toString();
                String aciklamaText=  ((EditText)findViewById(R.id.aciklama_edit)).getText().toString();
                int secilenmarkaid=((DegerIkilisi)mevcutMarkaSpinner.getSelectedItem()).getId();
                int secilenmodelid=((DegerIkilisi)mevcutModelSpinner.getSelectedItem()).getId();
                int secilensurumid=((DegerIkilisi)mevcutAndroidSurumSpinner.getSelectedItem()).getId();
                int secilenekranid=((DegerIkilisi)mevcutEkranSpinner.getSelectedItem()).getId();
                int secilenisyeriid=((DegerIkilisi)mevcutIsyeriSpinner.getSelectedItem()).getId();


                if (envnoText.length()==0){
                    Toast.makeText(YeniCihazKayit.this, "Envno Boş Geçilemez!", Toast.LENGTH_SHORT).show();
                }else if (secilenmarkaid==-1){
                    Toast.makeText(YeniCihazKayit.this, "Marka Seçimsiz Geçilemez!", Toast.LENGTH_SHORT).show();
                }else if (secilenmodelid==-1){
                    Toast.makeText(YeniCihazKayit.this, "Model Seçimsiz Geçilemez!", Toast.LENGTH_SHORT).show();
                } else if (secilenisyeriid==-1){
                    Toast.makeText(YeniCihazKayit.this, "İşyeri Seçimsiz Geçilemez!", Toast.LENGTH_SHORT).show();
                }else if (secilensurumid==-1){
                    Toast.makeText(YeniCihazKayit.this, "Sürüm No Seçimsiz Geçilemez!", Toast.LENGTH_SHORT).show();
                }else if (secilenekranid==-1){
                    Toast.makeText(YeniCihazKayit.this, "Ekran Seçimsiz Geçilemez!", Toast.LENGTH_SHORT).show();
                }else if (imeinoText.length()==0){
                    Toast.makeText(YeniCihazKayit.this, "İMEİNo Boş Geçilemez!", Toast.LENGTH_SHORT).show();
                }else if (gsmnoText.length()==0){
                    Toast.makeText(YeniCihazKayit.this, "GSMNo Boş Geçilemez!", Toast.LENGTH_SHORT).show();
                }else if (okc.envnovarmi(envnoText)){
                    Toast.makeText(YeniCihazKayit.this, "Bu envno kullanıldı!", Toast.LENGTH_SHORT).show();
                }else {
                    yzc.cihazYaz(envnoText,secilenmarkaid,secilenmodelid,secilensurumid, secilenisyeriid,gsmnoText,secilenekranid,imeinoText,aciklamaText);
                    Toast.makeText(YeniCihazKayit.this, "Kayıt Başarılı", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    //TODO ne işe yarıyor?
    protected void onResume(){
        super.onResume();

        Spinner mevcutMarkaSpinner=findViewById(R.id.spn_markaid);
        DropDownAdapter markaAdapter=new DropDownAdapter(this,okc.markaver());
        mevcutMarkaSpinner.setAdapter(markaAdapter);

        Spinner mevcutModelSpinner=findViewById(R.id.spn_modelid);
        DropDownAdapter modelAdapter=new DropDownAdapter(this,okc.modelver());
        mevcutModelSpinner.setAdapter(modelAdapter);

        Spinner mevcutIsyeriSpinner=findViewById(R.id.spn_isyeriid);
        DropDownAdapter isyeriAdapter=new DropDownAdapter(this,okc.isyerleriver());
        mevcutIsyeriSpinner.setAdapter(isyeriAdapter);

        Spinner mevcutAndroidSurumSpinner=findViewById(R.id.spn_surumid);
        DropDownAdapter androidAdapter=new DropDownAdapter(this,okc.tipgrubunaGoreTipleriver(SabitDegiskenler.ANDROID_SURUM_TIP));
        mevcutAndroidSurumSpinner.setAdapter(androidAdapter);

        Spinner mevcutEkranSpinner=findViewById(R.id.spn_ekkranid);
        DropDownAdapter ekranAdapter=new DropDownAdapter(this,okc.tipgrubunaGoreTipleriver(SabitDegiskenler.EKRAN_BOYUT_TIP));
        mevcutEkranSpinner.setAdapter(ekranAdapter);

    }
}