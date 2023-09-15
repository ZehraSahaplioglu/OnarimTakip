package com.example.onarimtakip;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
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
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Marka;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Model;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Okuyucu;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.SabitDegiskenler;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Yazici;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.YedekParca;

import java.util.ArrayList;

public class YedekParcaIslemleri extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yedek_parca_islemleri);

        ((TextView) findViewById(R.id.titleTextView)).setText("Yedek Parça Kayıt Sayfası");

        Yazici yzc= new Yazici(YedekParcaIslemleri.this);
        Okuyucu okc=new Okuyucu(YedekParcaIslemleri.this);
        Fonksiyonlar fnk=new Fonksiyonlar(YedekParcaIslemleri.this);

        // tip sınıfından yedekparca tip spinnirini aldık
        Spinner mevcutYedekParcaTiplerSpinner=findViewById(R.id.spn_yedekparcatip);
        DropDownAdapter tiplistesiadapter=new DropDownAdapter(this,okc.tipgrubunaGoreTipleriver(SabitDegiskenler.YEDEK_PARCA_TIP));
        mevcutYedekParcaTiplerSpinner.setAdapter(tiplistesiadapter);

        // yedek parca spinnirini tip spinnirina göre ayarlanacak
        Spinner mevcutYedekParcaSpinner=findViewById(R.id.spn_yedekparca);

        Spinner mevcutMarkaSpinner=findViewById(R.id.spn_marka);
        DropDownAdapter markaadapter=new DropDownAdapter(this,okc.markaver());
        mevcutMarkaSpinner.setAdapter(markaadapter);

        Spinner mevcutModelSpinner=findViewById(R.id.spn_model);


        mevcutMarkaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int secilenMarkaID = ((DegerIkilisi) mevcutMarkaSpinner.getSelectedItem()).getId();
                if (secilenMarkaID != -1){

                    DropDownAdapter modeladapter=new DropDownAdapter(YedekParcaIslemleri.this,
                            okc.markaIdyegoreModelListever(secilenMarkaID));

                    mevcutModelSpinner.setAdapter(modeladapter);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //TipTanımlama Sınıfındatanımlı olan
        mevcutYedekParcaTiplerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int secilenTipId=((DegerIkilisi) mevcutYedekParcaTiplerSpinner.getSelectedItem()).getId();

                if(secilenTipId!=-1) {

                    DropDownAdapter yedekparcaadapter=new DropDownAdapter(YedekParcaIslemleri.this,
                            okc.tipegoreYedekParcaListever(secilenTipId));

                    mevcutYedekParcaSpinner.setAdapter(yedekparcaadapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // Tip spinnirina göre burası dolacak
        // secilen spinnera göre sayfa dolacak otomatik
        mevcutYedekParcaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int secilenParcaId=((DegerIkilisi) mevcutYedekParcaSpinner.getSelectedItem()).getId();

                if(secilenParcaId!=-1) {

                    ((EditText) findViewById(R.id.parca_adi_edit)).setText(((DegerIkilisi) mevcutYedekParcaSpinner.getSelectedItem()).getText());

                    YedekParca secilenYedekParca = okc.idyegoreYedekParcaver(secilenParcaId);

                    fnk.spinnerSelector(mevcutMarkaSpinner, secilenYedekParca.getMarka_id());

                    DropDownAdapter modeladapter=new DropDownAdapter(YedekParcaIslemleri.this,
                            okc.markaIdyegoreModelListever( secilenYedekParca.getMarka_id()));
                    mevcutModelSpinner.setAdapter(modeladapter);

                    fnk.spinnerSelector(mevcutModelSpinner, secilenYedekParca.getModel_id());

                    ((EditText) findViewById(R.id.aciklama_edit)).setText(secilenYedekParca.getAciklama());
                    ((EditText) findViewById(R.id.editTextfiyat)).setText(String.valueOf(secilenYedekParca.getFiyat()));
                    ((EditText) findViewById(R.id.editTextstok)).setText(String.valueOf(secilenYedekParca.getStok_miktar()));
                    ((EditText) findViewById(R.id.editTextstokmin)).setText(String.valueOf(secilenYedekParca.getStokmin()));


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
                Intent intent = new Intent(YedekParcaIslemleri.this, TipTanimlama.class);
                intent.putExtra("tiptipi", SabitDegiskenler.YEDEK_PARCA_TIP);
                startActivity(intent);
            }
        });

        ((Button)findViewById(R.id.kaydet_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String adi, aciklama;
                double fiyat;
                int stok_miktar, stok_min;

                adi=((EditText) findViewById(R.id.parca_adi_edit)).getText().toString();
                aciklama=((EditText) findViewById(R.id.aciklama_edit)).getText().toString();
                fiyat=Double.parseDouble (((EditText) findViewById(R.id.editTextfiyat)).getText().toString());
                stok_miktar = Integer.parseInt(((EditText) findViewById(R.id.editTextstok)).getText().toString());
                stok_min = Integer.parseInt(((EditText) findViewById(R.id.editTextstokmin)).getText().toString());

                int secilenTipID = ((DegerIkilisi) mevcutYedekParcaTiplerSpinner.getSelectedItem()).getId();

                int markaid= ((DegerIkilisi)mevcutMarkaSpinner.getSelectedItem()).getId();
                int modelid= ((DegerIkilisi)mevcutModelSpinner.getSelectedItem()).getId();

                if (adi.length()==0){
                    Toast.makeText(YedekParcaIslemleri.this, "Adı Boş Geçilemez!", Toast.LENGTH_SHORT).show();
                }
                else if(fiyat==-1){
                    Toast.makeText(YedekParcaIslemleri.this, "Fiyat boş geçilemez!", Toast.LENGTH_SHORT).show();
                }
                else if(stok_miktar==-1){
                    Toast.makeText(YedekParcaIslemleri.this, "Stok miktarı boş geçilemez!", Toast.LENGTH_SHORT).show();
                }
                else if(stok_min==-1){
                    Toast.makeText(YedekParcaIslemleri.this, "Min stok değeri boş geçilemez!", Toast.LENGTH_SHORT).show();
                }
                else if(secilenTipID==-1){
                    Toast.makeText(YedekParcaIslemleri.this, "Yedek parça tipi seçimsiz geçilemez!", Toast.LENGTH_SHORT).show();
                }
                else if(markaid==-1){
                    Toast.makeText(YedekParcaIslemleri.this, "Marka seçimsiz geçilemez!", Toast.LENGTH_SHORT).show();
                }
                else if(modelid==-1){
                    Toast.makeText(YedekParcaIslemleri.this, "Model seçimsiz geçilemez!", Toast.LENGTH_SHORT).show();
                }
                else if (okc.ayniYedekParcavarmi(adi)){
                    Toast.makeText(YedekParcaIslemleri.this, "Bu model kullanıldı!", Toast.LENGTH_SHORT).show();
                }
                else {
                    yzc.yedekparcaYaz(markaid, modelid, adi, fiyat,stok_min, stok_miktar, aciklama, secilenTipID);
                    Toast.makeText(YedekParcaIslemleri.this, "Kayıt Başarılı", Toast.LENGTH_SHORT).show();

                }
            }
        });

        ((Button)findViewById(R.id.guncelle_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String adi, aciklama;
                double fiyat;
                int stok_miktar, stok_min;

                int secilenTipID = ((DegerIkilisi) mevcutYedekParcaTiplerSpinner.getSelectedItem()).getId();
                int secilenParcaID = ((DegerIkilisi) mevcutYedekParcaSpinner.getSelectedItem()).getId();

                adi=((EditText) findViewById(R.id.parca_adi_edit)).getText().toString();
                aciklama=((EditText) findViewById(R.id.aciklama_edit)).getText().toString();
                fiyat=Double.parseDouble (((EditText) findViewById(R.id.editTextfiyat)).getText().toString());
                stok_miktar = Integer.parseInt(((EditText) findViewById(R.id.editTextstok)).getText().toString());
                stok_min = Integer.parseInt(((EditText) findViewById(R.id.editTextstokmin)).getText().toString());

                int markaid= ((DegerIkilisi)mevcutMarkaSpinner.getSelectedItem()).getId();
                int modelid= ((DegerIkilisi)mevcutModelSpinner.getSelectedItem()).getId();

                if (adi.length()==0){
                    Toast.makeText(YedekParcaIslemleri.this, "Adı Boş Geçilemez!", Toast.LENGTH_SHORT).show();
                }
                else if(fiyat==-1){
                    Toast.makeText(YedekParcaIslemleri.this, "Fiyat boş geçilemez!", Toast.LENGTH_SHORT).show();
                }
                else if(stok_miktar==-1){
                    Toast.makeText(YedekParcaIslemleri.this, "Stok miktarı boş geçilemez!", Toast.LENGTH_SHORT).show();
                }
                else if(stok_min==-1){
                    Toast.makeText(YedekParcaIslemleri.this, "Min stok değeri boş geçilemez!", Toast.LENGTH_SHORT).show();
                }
                else if(secilenTipID==-1){
                    Toast.makeText(YedekParcaIslemleri.this, "Yedek parça tipi seçimsiz geçilemez!", Toast.LENGTH_SHORT).show();
                }
                else if(secilenParcaID==-1){
                    Toast.makeText(YedekParcaIslemleri.this, "Yedek parça seçimsiz geçilemez!", Toast.LENGTH_SHORT).show();
                }
                else if(markaid==-1){
                    Toast.makeText(YedekParcaIslemleri.this, "Marka seçimsiz geçilemez!", Toast.LENGTH_SHORT).show();
                }
                else if(modelid==-1){
                    Toast.makeText(YedekParcaIslemleri.this, "Model seçimsiz geçilemez!", Toast.LENGTH_SHORT).show();
                }
                else if(okc.ayniYedekParcavarmi(adi, secilenParcaID)){
                    Toast.makeText(YedekParcaIslemleri.this, "Bu parça kullanıldı!", Toast.LENGTH_SHORT).show();
                }
                else {
                    yzc.yedekParcaGuncelle(secilenParcaID, markaid, modelid, adi, fiyat, stok_min, stok_miktar, aciklama, secilenTipID);
                    Toast.makeText(YedekParcaIslemleri.this, "Güncelleme başarılı", Toast.LENGTH_SHORT).show();
                }

            }
        });

        ((Button)findViewById(R.id.sil_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int curYedekParca= ((DegerIkilisi)mevcutYedekParcaSpinner.getSelectedItem()).getId();

                AlertDialog.Builder silmeDialog = new AlertDialog.Builder(YedekParcaIslemleri.this);
                silmeDialog.setMessage("Bu yedek parçayı silmek istediğinizden emin misiniz?")
                        .setTitle("Yedek parça silinecek!")
                        .setCancelable(false)
                        .setIcon(R.drawable.warning_24)
                        .setPositiveButton("Evet", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                //Silme işlemi bu kısma kodlanacak
                                yzc.yedekParcaSil(curYedekParca);
                                Toast.makeText(YedekParcaIslemleri.this, "Silme İşlemi Başarılı!", Toast.LENGTH_SHORT).show();
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