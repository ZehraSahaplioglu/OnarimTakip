package com.example.onarimtakip;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onarimtakip.Tanimlamalar.DegerIkilisi;
import com.example.onarimtakip.Tanimlamalar.DropDownAdapter;
import com.example.onarimtakip.Tanimlamalar.Fonksiyonlar;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Marka;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Model;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Okuyucu;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Yazici;

public class MarkaTanimlama extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marka_tanimlama);

        ((TextView) findViewById(R.id.titleTextView)).setText("Marka Sayfası");

        Yazici yzc= new Yazici(MarkaTanimlama.this);
        Okuyucu okc=new Okuyucu(MarkaTanimlama.this);
        Fonksiyonlar fnk=new Fonksiyonlar(MarkaTanimlama.this);

        Spinner mevcutMarkaSpinner=findViewById(R.id.spn_marka);
        DropDownAdapter markaadapter=new DropDownAdapter(this,okc.markaver());
        mevcutMarkaSpinner.setAdapter(markaadapter);

        mevcutMarkaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int secilenMarkaid=((DegerIkilisi) mevcutMarkaSpinner.getSelectedItem()).getId();
                if(secilenMarkaid!=-1) {
                    ((EditText) findViewById(R.id.marka_adi_edit)).setText(((DegerIkilisi) mevcutMarkaSpinner.getSelectedItem()).getText());
                    Marka secilenMarka = okc.idyegoremarkaver(secilenMarkaid);
                    fnk.spinnerSelector(mevcutMarkaSpinner, secilenMarka.getId());
                    ((EditText) findViewById(R.id.marka_aciklama_edit)).setText(secilenMarka.getAciklama());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ((Button)findViewById(R.id.kaydet_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String adi, aciklama;
                adi=((EditText) findViewById(R.id.marka_adi_edit)).getText().toString();
                aciklama=((EditText) findViewById(R.id.marka_aciklama_edit)).getText().toString();

                if (okc.aynimarkavarmi(adi)){
                    Toast.makeText(MarkaTanimlama.this, "Bu marka kayıtlı!", Toast.LENGTH_SHORT).show();
                }else {
                    yzc.markaYaz(adi, aciklama);
                    Toast.makeText(MarkaTanimlama.this, "Kayıt Başarılı.", Toast.LENGTH_SHORT).show();

                }
            }
        });

        ((Button)findViewById(R.id.guncelle_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String adi, aciklama;
                int secilenMarkaId=((DegerIkilisi) mevcutMarkaSpinner.getSelectedItem()).getId();
                adi=((EditText) findViewById(R.id.marka_adi_edit)).getText().toString();
                aciklama=((EditText) findViewById(R.id.marka_aciklama_edit)).getText().toString();

                if (okc.aynimarkavarmi(adi, secilenMarkaId)){
                    Toast.makeText(MarkaTanimlama.this, "Bu marka mevcut!", Toast.LENGTH_SHORT).show();
                }else {
                    yzc.markaGuncelle(adi,aciklama,secilenMarkaId);
                    Toast.makeText(MarkaTanimlama.this, "Güncelleme Başarılı.", Toast.LENGTH_SHORT).show();

                }

                startActivity(new Intent(MarkaTanimlama.this, MarkaTanimlama.class));
                //Toast.makeText(MarkaTanimlama.this,"Güncelleme Başarılıdır.", Toast.LENGTH_SHORT).show();
            }
        });

        ((Button)findViewById(R.id.sil_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int curMarka= ((DegerIkilisi)mevcutMarkaSpinner.getSelectedItem()).getId();

                AlertDialog.Builder silmeDialog = new AlertDialog.Builder(MarkaTanimlama.this);
                AlertDialog silDialog;
                silmeDialog.setMessage("Markayı silmek istediğinizden emin misiniz?")
                        .setTitle("Marka silinecek!")
                        .setCancelable(false)
                        .setIcon(R.drawable.warning_24)
                        .setPositiveButton("Evet", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                //Silme işlemi bu kısma kodlanacak
                                yzc.markaSil(curMarka);
                                Toast.makeText(MarkaTanimlama.this, "Silme İşlemi Başarılı!", Toast.LENGTH_SHORT).show();
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

//TODO Tip,Marka, İşyeri,Cihaz,Model,Yedekparça,Onarım kayıtları silinmeden önce diğrer tablolarda kullanılıp kullanılmadığı tespit edilip silinecek.


    }
}