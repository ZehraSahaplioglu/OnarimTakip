package com.example.onarimtakip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onarimtakip.Tanimlamalar.DegerIkilisi;
import com.example.onarimtakip.Tanimlamalar.DropDownAdapter;
import com.example.onarimtakip.Tanimlamalar.Fonksiyonlar;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Cihaz;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Okuyucu;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.SabitDegiskenler;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Yazici;

public class GuncelleKayit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guncelle_kayit);

        ((TextView) findViewById(R.id.titleTextView)).setText("Kayıt Güncelleme Sayfası");

        // başka sayfadan veri aldık
        Bundle bndl=getIntent().getExtras();
        int guncellenecekCihazID=bndl.getInt("cihaz_id");

        Yazici yzc= new Yazici(GuncelleKayit.this);
        Okuyucu okc=new Okuyucu(GuncelleKayit.this);
        Fonksiyonlar fnk=new Fonksiyonlar(GuncelleKayit.this);

        //Cihaz sınıfından yeni bir değişken oluşturduk
        Cihaz guncellenecekCihaz=okc.idyegoreCihazver(guncellenecekCihazID);

        // burada guncellenmek istenen cihazın başka sayfada kayıtedilen text bilgilerini bu sayfaya aldık
        ((EditText) findViewById(R.id.envno_edit)).setText(guncellenecekCihaz.getEnvno());
        ((EditText) findViewById(R.id.imeino_edit)).setText(guncellenecekCihaz.getImei_no());
        ((EditText) findViewById(R.id.gsmno_edit)).setText(guncellenecekCihaz.getGsm_no());
        ((EditText) findViewById(R.id.aciklama_edit)).setText(guncellenecekCihaz.getAciklama());

        //Marka Tanımlama
        // önceden kaydettiğimiz cihazın marka bilgisini ekrana yazdırıyoruz
        Spinner mevcutMarkaSpinner=findViewById(R.id.spn_markaid);
        DropDownAdapter markaAdapter=new DropDownAdapter(this,okc.markaver());
        mevcutMarkaSpinner.setAdapter(markaAdapter);
        fnk.spinnerSelector(mevcutMarkaSpinner,guncellenecekCihaz.getMarka_id());

        ImageButton imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GuncelleKayit.this, MarkaTanimlama.class);
                startActivity(intent);
            }
        });

        //Model Tanımlama
        Yazici yzc2= new Yazici(GuncelleKayit.this);
        Okuyucu okc2=new Okuyucu(GuncelleKayit.this);

        Spinner mevcutModelSpinner=findViewById(R.id.spn_modelid);
        DropDownAdapter modelAdapter=new DropDownAdapter(this,okc2.modelver());
        mevcutModelSpinner.setAdapter(modelAdapter);
        fnk.spinnerSelector(mevcutModelSpinner,guncellenecekCihaz.getModel_id());

        ImageButton imageButton2 = findViewById(R.id.imageButton2);
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GuncelleKayit.this, ModelTanimlama.class);
                startActivity(intent);
            }
        });

        //İsyeri Islemleri
        Yazici yzc3= new Yazici(GuncelleKayit.this);
        Okuyucu okc3=new Okuyucu(GuncelleKayit.this);

        Spinner mevcutIsyeriSpinner=findViewById(R.id.spn_isyeriid);
        DropDownAdapter isyeriAdapter=new DropDownAdapter(this,okc3.isyerleriver());
        mevcutIsyeriSpinner.setAdapter(isyeriAdapter);
        fnk.spinnerSelector(mevcutIsyeriSpinner,guncellenecekCihaz.getIsyeri_id());

        ImageButton imageButton3 = findViewById(R.id.imageButton3);
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GuncelleKayit.this, IsyeriIslemleri.class);
                startActivity(intent);
            }
        });

        // Android Sürüm
        Yazici yzc4= new Yazici(GuncelleKayit.this);
        Okuyucu okc4=new Okuyucu(GuncelleKayit.this);

        Spinner mevcutAndroidSurumSpinner=findViewById(R.id.spn_surumid);
        DropDownAdapter androidAdapter=new DropDownAdapter(this,okc4.tipgrubunaGoreTipleriver(SabitDegiskenler.ANDROID_SURUM_TIP));
        mevcutAndroidSurumSpinner.setAdapter(androidAdapter);
        fnk.spinnerSelector(mevcutAndroidSurumSpinner,guncellenecekCihaz.getAndroidsurum_id());

        ImageButton imageButton4 = findViewById(R.id.imageButton4);
        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GuncelleKayit.this, TipTanimlama.class);
                startActivity(intent);
            }
        });

        // Ekran Boyutu
        Yazici yzc5= new Yazici(GuncelleKayit.this);
        Okuyucu okc5=new Okuyucu(GuncelleKayit.this);

        Spinner mevcutEkranSpinner=findViewById(R.id.spn_ekkranid);
        DropDownAdapter ekranAdapter=new DropDownAdapter(this,okc5.tipgrubunaGoreTipleriver(SabitDegiskenler.EKRAN_BOYUT_TIP));
        mevcutEkranSpinner.setAdapter(ekranAdapter);
        fnk.spinnerSelector(mevcutEkranSpinner,guncellenecekCihaz.getEkranboyutu_id());


        ImageButton imageButton5 = findViewById(R.id.imageButton5);
        imageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GuncelleKayit.this, TipTanimlama.class);
                startActivity(intent);
            }
        });

        //Guncelle Butonu
        ((Button)findViewById(R.id.guncelle_button)).setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(GuncelleKayit.this, "Envno Boş Geçilemez!", Toast.LENGTH_SHORT).show();
                }else if (secilenmarkaid==-1){
                    Toast.makeText(GuncelleKayit.this, "Marka Seçimsiz Geçilemez!", Toast.LENGTH_SHORT).show();
                }else if (secilenmodelid==-1){
                    Toast.makeText(GuncelleKayit.this, "Model Seçimsiz Geçilemez!", Toast.LENGTH_SHORT).show();
                } else if (secilenisyeriid==-1){
                    Toast.makeText(GuncelleKayit.this, "İşyeri Seçimsiz Geçilemez!", Toast.LENGTH_SHORT).show();
                }else if (secilensurumid==-1){
                    Toast.makeText(GuncelleKayit.this, "Sürüm No Seçimsiz Geçilemez!", Toast.LENGTH_SHORT).show();
                }else if (secilenekranid==-1){
                    Toast.makeText(GuncelleKayit.this, "Ekran Seçimsiz Geçilemez!", Toast.LENGTH_SHORT).show();
                }else if (imeinoText.length()==0){
                    Toast.makeText(GuncelleKayit.this, "İMEİNo Boş Geçilemez!", Toast.LENGTH_SHORT).show();
                }else if (gsmnoText.length()==0){
                    Toast.makeText(GuncelleKayit.this, "GSMNo Boş Geçilemez!", Toast.LENGTH_SHORT).show();
                }
                else if (okc.envnovarmi(envnoText,guncellenecekCihaz.getId())){
                    Toast.makeText(GuncelleKayit.this, "!", Toast.LENGTH_SHORT).show();
                }
                else {
                    yzc.cihazGuncelle(envnoText,secilenmarkaid, secilenmodelid, secilensurumid, secilenisyeriid, gsmnoText,
                            secilenekranid, imeinoText, aciklamaText,guncellenecekCihaz.getId());
                    Toast.makeText(GuncelleKayit.this, "Güncelleme Başarılı.", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}