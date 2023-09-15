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
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Isyeri;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Marka;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Okuyucu;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Yazici;

public class IsyeriIslemleri extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isyeri_islemleri);

        ((TextView) findViewById(R.id.titleTextView)).setText("İşyeri Sayfası");

        // Burada Yazıcı ve Okuyucu class'larındaki fonksiyonları kullanacağız.

        Yazici yzc= new Yazici(IsyeriIslemleri.this);
        Okuyucu okc=new Okuyucu(IsyeriIslemleri.this);
        Fonksiyonlar fnk=new Fonksiyonlar(IsyeriIslemleri.this);

        Spinner mevcutIsyeriSpinner=findViewById(R.id.spn_isyeri);
        DropDownAdapter isyeriadapter=new DropDownAdapter(this,okc.isyerleriver());
        mevcutIsyeriSpinner.setAdapter(isyeriadapter);

        mevcutIsyeriSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int secilenIsyeriid=((DegerIkilisi) mevcutIsyeriSpinner.getSelectedItem()).getId();
                if(secilenIsyeriid!=-1) {
                    ((EditText) findViewById(R.id.isyeri_adi_edit)).setText(((DegerIkilisi) mevcutIsyeriSpinner.getSelectedItem()).getText());
                    Isyeri secilenIsyeri = okc.idyegoreisyeriver(secilenIsyeriid);
                    fnk.spinnerSelector(mevcutIsyeriSpinner, secilenIsyeri.getId());
                    ((EditText) findViewById(R.id.isyeri_aciklama_edit)).setText(secilenIsyeri.getAciklama());
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
                adi=((EditText) findViewById(R.id.isyeri_adi_edit)).getText().toString();
                aciklama=((EditText) findViewById(R.id.isyeri_aciklama_edit)).getText().toString();

                if (okc.ayniisyerivarmi(adi)){
                    Toast.makeText(IsyeriIslemleri.this, "Bu işyeri ismi kayıtlı!", Toast.LENGTH_SHORT).show();
                }else {
                    yzc.isyeriYaz(adi, aciklama);
                    Toast.makeText(IsyeriIslemleri.this, "Kayıt Başarılı.", Toast.LENGTH_SHORT).show();

                }

            }
        });

        ((Button)findViewById(R.id.guncelle_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String adi, aciklama;
                int secilenIsyeriId=((DegerIkilisi) mevcutIsyeriSpinner.getSelectedItem()).getId();
                adi=((EditText) findViewById(R.id.isyeri_adi_edit)).getText().toString();
                aciklama=((EditText) findViewById(R.id.isyeri_aciklama_edit)).getText().toString();

                if (okc.ayniisyerivarmi(adi, secilenIsyeriId)){
                    Toast.makeText(IsyeriIslemleri.this, "Bu isyeri mevcut!", Toast.LENGTH_SHORT).show();
                }else {
                    yzc.isyeriGuncelle(adi,aciklama,secilenIsyeriId);
                    Toast.makeText(IsyeriIslemleri.this, "Güncelleme Başarılı.", Toast.LENGTH_SHORT).show();

                }

                startActivity(new Intent(IsyeriIslemleri.this, IsyeriIslemleri.class));
            }
        });

        ((Button)findViewById(R.id.sil_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int curIsyeri= ((DegerIkilisi)mevcutIsyeriSpinner.getSelectedItem()).getId();

                AlertDialog.Builder silmeDialog = new AlertDialog.Builder(IsyeriIslemleri.this);
                AlertDialog silDialog;
                silmeDialog.setMessage("Bu işyerini silmek istediğinizden emin misiniz?")
                        .setTitle("İşyeri silinecek")
                        .setCancelable(false)
                        .setIcon(R.drawable.warning_24)
                        .setPositiveButton("Evet", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                //Silme işlemi bu kısma kodlanacak
                                yzc.isyeriSil(curIsyeri);
                                Toast.makeText(IsyeriIslemleri.this, "Silme İşlemi Başarılı!", Toast.LENGTH_SHORT).show();
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
