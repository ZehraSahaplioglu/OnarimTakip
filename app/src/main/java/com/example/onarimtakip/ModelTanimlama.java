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
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Cihaz;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Marka;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Model;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Okuyucu;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Yazici;

import java.util.ArrayList;

public class ModelTanimlama extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_tanimlama);

        Okuyucu okc=new Okuyucu(ModelTanimlama.this);
        Yazici yzc= new Yazici(ModelTanimlama.this);
        Fonksiyonlar fnk=new Fonksiyonlar(ModelTanimlama.this);

        ((TextView) findViewById(R.id.titleTextView)).setText("Model Sayfası");

        Spinner mevcutMarkaSpinner=findViewById(R.id.spn_marka);
        DropDownAdapter markaadapter=new DropDownAdapter(this,okc.markaver());
        mevcutMarkaSpinner.setAdapter(markaadapter);

        Spinner mevcutModelSpinner=findViewById(R.id.spn_model);


        mevcutMarkaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int secilenMarkaID = ((DegerIkilisi) mevcutMarkaSpinner.getSelectedItem()).getId();
                if (secilenMarkaID != -1){

                    DropDownAdapter modeladapter=new DropDownAdapter(ModelTanimlama.this,
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

                    ((EditText) findViewById(R.id.model_adi_edit)).setText(((DegerIkilisi) mevcutModelSpinner.getSelectedItem()).getText());
                    Model secilenModel = okc.idyegoremodelver(secilenModelId);
                    fnk.spinnerSelector(mevcutMarkaSpinner, secilenModel.getMarka_id());
                    ((EditText) findViewById(R.id.model_aciklama_edit)).setText(secilenModel.getAciklama());

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
                adi=((EditText) findViewById(R.id.model_adi_edit)).getText().toString();
                aciklama=((EditText) findViewById(R.id.model_aciklama_edit)).getText().toString();
                int markaid= ((DegerIkilisi)mevcutMarkaSpinner.getSelectedItem()).getId();

                if (okc.aynimodelvarmi(adi)){
                    Toast.makeText(ModelTanimlama.this, "Bu model kayıtlı!", Toast.LENGTH_SHORT).show();
                }else {
                    yzc.modelYaz(adi, aciklama, markaid);
                    Toast.makeText(ModelTanimlama.this, "Kayıt Başarılı.", Toast.LENGTH_SHORT).show();

                }
                //startActivity(new Intent(ModelTanimlama.this, ModelTanimlama.class));
            }
        });

        ((Button)findViewById(R.id.guncelle_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String adi, aciklama;
                int secilenModelId=((DegerIkilisi) mevcutModelSpinner.getSelectedItem()).getId();
                adi=((EditText) findViewById(R.id.model_adi_edit)).getText().toString();
                aciklama=((EditText) findViewById(R.id.model_aciklama_edit)).getText().toString();
                int markaid= ((DegerIkilisi)mevcutMarkaSpinner.getSelectedItem()).getId();

                if (okc.aynimodelvarmi(adi, secilenModelId)){
                    Toast.makeText(ModelTanimlama.this, "Bu model mevcut!", Toast.LENGTH_SHORT).show();
                }else {
                    yzc.modelGuncelle(adi,aciklama,markaid,secilenModelId);
                    Toast.makeText(ModelTanimlama.this, "Güncelleme Başarılı.", Toast.LENGTH_SHORT).show();

                }

                startActivity(new Intent(ModelTanimlama.this, ModelTanimlama.class));
            }
        });

        ((Button)findViewById(R.id.sil_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int curModel= ((DegerIkilisi)mevcutModelSpinner.getSelectedItem()).getId();

                AlertDialog.Builder silmeDialog = new AlertDialog.Builder(ModelTanimlama.this);
                AlertDialog silDialog;
                silmeDialog.setMessage("Bu modeli silmek istediğinizden emin misiniz?")
                        .setTitle("Model silinecek!")
                        .setCancelable(false)
                        .setIcon(R.drawable.warning_24)
                        .setPositiveButton("Evet", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                //Silme işlemi bu kısma kodlanacak
                                yzc.modelSil(curModel);
                                Toast.makeText(ModelTanimlama.this, "Silme İşlemi Başarılı!", Toast.LENGTH_SHORT).show();
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