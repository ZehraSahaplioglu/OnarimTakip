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
import com.example.onarimtakip.Tanimlamalar.VeriTabani.DinamikTipler;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Okuyucu;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.SabitDegiskenler;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Yazici;

public class TipTanimlama extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_tanimlama);

        Yazici yzc= new Yazici(TipTanimlama.this);
        Fonksiyonlar fnk=new Fonksiyonlar(TipTanimlama.this);
        Intent intt=getIntent();
        int tiptipi = intt.getIntExtra("tiptipi",0);

        Okuyucu okc=new Okuyucu(TipTanimlama.this);

        if(tiptipi== SabitDegiskenler.ANDROID_SURUM_TIP){

            Spinner mevcutTiplerSpinner=findViewById(R.id.mevcuttiplerspinner);
            DropDownAdapter tiplistesiadapter=new DropDownAdapter(this, okc.tipgrubunaGoreTipleriver(SabitDegiskenler.ANDROID_SURUM_TIP));
            mevcutTiplerSpinner.setAdapter(tiplistesiadapter);

            mevcutTiplerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    int secilenTipid=((DegerIkilisi) mevcutTiplerSpinner.getSelectedItem()).getId();
                    if(secilenTipid != -1) {
                        ((EditText) findViewById(R.id.tip_adi_edit)).setText(((DegerIkilisi) mevcutTiplerSpinner.getSelectedItem()).getText());
                        DinamikTipler secilenTip = okc.idyegoretipver(secilenTipid);
                        fnk.spinnerSelector(mevcutTiplerSpinner, secilenTip.getId());
                        ((EditText) findViewById(R.id.tip_aciklama_edit)).setText(secilenTip.getAciklama());
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            ((TextView) findViewById(R.id.titleTextView)).setText("Android Sürüm Tanımlama");
            ((EditText) findViewById(R.id.tip_adi_edit)).setHint("Android Sürüm Adı Giriniz: ");
            ((EditText) findViewById(R.id.tip_aciklama_edit)).setHint("Açıklama Giriniz: ");

            ((Button)findViewById(R.id.tip_kaydet_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String adi, aciklama;
                    adi=((EditText) findViewById(R.id.tip_adi_edit)).getText().toString();
                    aciklama=((EditText) findViewById(R.id.tip_aciklama_edit)).getText().toString();

                    if (okc.aynitipadieditvarmi(adi)){
                        Toast.makeText(TipTanimlama.this, "Bu android sürüm kayıtlı!", Toast.LENGTH_SHORT).show();
                    }else {
                        yzc.tipYaz(adi, aciklama,SabitDegiskenler.ANDROID_SURUM_TIP);
                        Toast.makeText(TipTanimlama.this, "Kayıt Başarılı.", Toast.LENGTH_SHORT).show();

                    }

                }
            });

            ((Button)findViewById(R.id.tip_guncelle_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String adi, aciklama;
                    int secilenTipId=((DegerIkilisi) mevcutTiplerSpinner.getSelectedItem()).getId();
                    adi=((EditText) findViewById(R.id.tip_adi_edit)).getText().toString();
                    aciklama=((EditText) findViewById(R.id.tip_aciklama_edit)).getText().toString();

                    if (okc.aynitipadieditvarmi(adi, secilenTipId)){
                        Toast.makeText(TipTanimlama.this, "Bu android sürüm mevcut!", Toast.LENGTH_SHORT).show();
                    }else {
                        yzc.tiptanimlamaGuncelle(adi,aciklama,secilenTipId);
                        Toast.makeText(TipTanimlama.this, "Güncelleme Başarılı!", Toast.LENGTH_SHORT).show();

                    }

                    startActivity(new Intent(TipTanimlama.this, TipTanimlama.class));
                }
            });

            ((Button)findViewById(R.id.tip_sil_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int curTipid= ((DegerIkilisi)mevcutTiplerSpinner.getSelectedItem()).getId();

                    AlertDialog.Builder silmeDialog = new AlertDialog.Builder(TipTanimlama.this);
                    silmeDialog.setMessage("Android sürüm tipini silmek istediğinizden emin misiniz?")
                            .setTitle("Android sürüm silinecek!")
                            .setCancelable(false)
                            .setIcon(R.drawable.warning_24)
                            .setPositiveButton("Evet", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    //Silme işlemi bu kısma kodlanacak
                                    yzc.tiptanimlamaSil(curTipid);
                                    Toast.makeText(TipTanimlama.this, "Silme İşlemi Başarılı!", Toast.LENGTH_SHORT).show();
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

        else if (tiptipi== SabitDegiskenler.EKRAN_BOYUT_TIP) {

            Spinner mevcutTiplerSpinner=findViewById(R.id.mevcuttiplerspinner);
            DropDownAdapter tiplistesiadapter=new DropDownAdapter(this,okc.tipgrubunaGoreTipleriver(SabitDegiskenler.EKRAN_BOYUT_TIP));
            mevcutTiplerSpinner.setAdapter(tiplistesiadapter);

            mevcutTiplerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    int secilenTipid=((DegerIkilisi) mevcutTiplerSpinner.getSelectedItem()).getId();
                    if(secilenTipid!=-1) {
                        ((EditText) findViewById(R.id.tip_adi_edit)).setText(((DegerIkilisi) mevcutTiplerSpinner.getSelectedItem()).getText());
                        DinamikTipler secilenTip = okc.idyegoretipver(secilenTipid);
                        fnk.spinnerSelector(mevcutTiplerSpinner, secilenTip.getId());
                        ((EditText) findViewById(R.id.tip_aciklama_edit)).setText(secilenTip.getAciklama());
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            ((TextView) findViewById(R.id.titleTextView)).setText("Ekran Boyutu Tanımlama");
            ((EditText) findViewById(R.id.tip_adi_edit)).setHint("Ekran Boyutu Giriniz: ");
            ((EditText) findViewById(R.id.tip_aciklama_edit)).setHint("Açıklama Giriniz: ");

            ((Button)findViewById(R.id.tip_kaydet_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String adi, aciklama;
                    adi=((EditText) findViewById(R.id.tip_adi_edit)).getText().toString();
                    aciklama=((EditText) findViewById(R.id.tip_aciklama_edit)).getText().toString();

                    if (okc.aynitipadieditvarmi(adi)){
                        Toast.makeText(TipTanimlama.this, "Bu ekran boyutu kayıtlı!", Toast.LENGTH_SHORT).show();
                    }else {
                        yzc.tipYaz(adi, aciklama,SabitDegiskenler.EKRAN_BOYUT_TIP);
                        Toast.makeText(TipTanimlama.this, "Kayıt Başarılı.", Toast.LENGTH_SHORT).show();

                    }

                }
            });

            ((Button)findViewById(R.id.tip_guncelle_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String adi, aciklama;
                    int secilenTipId=((DegerIkilisi) mevcutTiplerSpinner.getSelectedItem()).getId();
                    adi=((EditText) findViewById(R.id.tip_adi_edit)).getText().toString();
                    aciklama=((EditText) findViewById(R.id.tip_aciklama_edit)).getText().toString();

                    if (okc.aynitipadieditvarmi(adi, secilenTipId)){
                        Toast.makeText(TipTanimlama.this, "Bu ekran boyutu mevcut!", Toast.LENGTH_SHORT).show();
                    }else {
                        yzc.tiptanimlamaGuncelle(adi,aciklama,secilenTipId);
                        Toast.makeText(TipTanimlama.this, "Kayıt Başarılı.", Toast.LENGTH_SHORT).show();

                    }

                    startActivity(new Intent(TipTanimlama.this, TipTanimlama.class));
                }
            });

            ((Button)findViewById(R.id.tip_sil_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int curTip= ((DegerIkilisi)mevcutTiplerSpinner.getSelectedItem()).getId();

                    AlertDialog.Builder silmeDialog = new AlertDialog.Builder(TipTanimlama.this);
                    AlertDialog silDialog;
                    silmeDialog.setMessage("Ekran boyutunu silmek istediğinizden emin misiniz?")
                            .setTitle("Ekran boyutu silinecek!")
                            .setCancelable(false)
                            .setIcon(R.drawable.warning_24)
                            .setPositiveButton("Evet", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    //Silme işlemi bu kısma kodlanacak
                                    yzc.tiptanimlamaSil(curTip);
                                    Toast.makeText(TipTanimlama.this, "Silme İşlemi Başarılı!", Toast.LENGTH_SHORT).show();
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

        else if (tiptipi== SabitDegiskenler.ARIZA_TIP) {

            Spinner mevcutTiplerSpinner=findViewById(R.id.mevcuttiplerspinner);
            DropDownAdapter tiplistesiadapter=new DropDownAdapter(this,okc.tipgrubunaGoreTipleriver(SabitDegiskenler.ARIZA_TIP));
            mevcutTiplerSpinner.setAdapter(tiplistesiadapter);

            mevcutTiplerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    int secilenTipid=((DegerIkilisi) mevcutTiplerSpinner.getSelectedItem()).getId();
                    if(secilenTipid!=-1) {
                        ((EditText) findViewById(R.id.tip_adi_edit)).setText(((DegerIkilisi) mevcutTiplerSpinner.getSelectedItem()).getText());
                        DinamikTipler secilenTip = okc.idyegoretipver(secilenTipid);
                        fnk.spinnerSelector(mevcutTiplerSpinner, secilenTip.getId());
                        ((EditText) findViewById(R.id.tip_aciklama_edit)).setText(secilenTip.getAciklama());
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            ((TextView) findViewById(R.id.titleTextView)).setText("Arıza Tipi Tanımlama");
            ((EditText) findViewById(R.id.tip_adi_edit)).setHint("Arıza Tipi Giriniz: ");
            ((EditText) findViewById(R.id.tip_aciklama_edit)).setHint("Açıklama Giriniz: ");


                ((Button)findViewById(R.id.tip_kaydet_button)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String adi, aciklama;
                        adi=((EditText) findViewById(R.id.tip_adi_edit)).getText().toString();
                        aciklama=((EditText) findViewById(R.id.tip_aciklama_edit)).getText().toString();

                        if (okc.aynitipadieditvarmi(adi)){
                            Toast.makeText(TipTanimlama.this, "Bu arıza tipi kayıtlı!", Toast.LENGTH_SHORT).show();
                        }else {
                            yzc.tipYaz(adi, aciklama,SabitDegiskenler.ARIZA_TIP);
                            Toast.makeText(TipTanimlama.this, "Kayıt Başarılı.", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

                ((Button)findViewById(R.id.tip_guncelle_button)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String adi, aciklama;
                        int secilenTipId=((DegerIkilisi) mevcutTiplerSpinner.getSelectedItem()).getId();
                        adi=((EditText) findViewById(R.id.tip_adi_edit)).getText().toString();
                        aciklama=((EditText) findViewById(R.id.tip_aciklama_edit)).getText().toString();

                        if (okc.aynitipadieditvarmi(adi, secilenTipId)){
                            Toast.makeText(TipTanimlama.this, "Bu arıza tipi mevcut!", Toast.LENGTH_SHORT).show();
                        }else {
                            yzc.tiptanimlamaGuncelle(adi,aciklama,secilenTipId);
                            Toast.makeText(TipTanimlama.this, "Kayıt Başarılı.", Toast.LENGTH_SHORT).show();

                        }

                        startActivity(new Intent(TipTanimlama.this, TipTanimlama.class));
                    }
                });

                ((Button)findViewById(R.id.tip_sil_button)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int curTip= ((DegerIkilisi)mevcutTiplerSpinner.getSelectedItem()).getId();

                        AlertDialog.Builder silmeDialog = new AlertDialog.Builder(TipTanimlama.this);
                        AlertDialog silDialog;
                        silmeDialog.setMessage("Arıza tipini silmek istediğinizden emin misiniz ?")
                                .setTitle("Arıza tipi silinecek!")
                                .setCancelable(false)
                                .setIcon(R.drawable.warning_24)
                                .setPositiveButton("Evet", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        //Silme işlemi bu kısma kodlanacak
                                        yzc.tiptanimlamaSil(curTip);
                                        Toast.makeText(TipTanimlama.this, "Silme İşlemi Başarılı!", Toast.LENGTH_SHORT).show();
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

        else if (tiptipi== SabitDegiskenler.ONARIM_DURUM_TIP) {

            Spinner mevcutTiplerSpinner=findViewById(R.id.mevcuttiplerspinner);
            DropDownAdapter tiplistesiadapter=new DropDownAdapter(this,okc.tipgrubunaGoreTipleriver(SabitDegiskenler.ONARIM_DURUM_TIP));
            mevcutTiplerSpinner.setAdapter(tiplistesiadapter);

            mevcutTiplerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    int secilenTipid=((DegerIkilisi) mevcutTiplerSpinner.getSelectedItem()).getId();
                    if(secilenTipid!=-1) {
                        ((EditText) findViewById(R.id.tip_adi_edit)).setText(((DegerIkilisi) mevcutTiplerSpinner.getSelectedItem()).getText());
                        DinamikTipler secilenTip = okc.idyegoretipver(secilenTipid);
                        fnk.spinnerSelector(mevcutTiplerSpinner, secilenTip.getId());
                        ((EditText) findViewById(R.id.tip_aciklama_edit)).setText(secilenTip.getAciklama());
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            ((TextView) findViewById(R.id.titleTextView)).setText("Onarım Durum Tipi Tanımlama");
            ((EditText) findViewById(R.id.tip_adi_edit)).setHint("Onarım Durum Tipi Giriniz: ");
            ((EditText) findViewById(R.id.tip_aciklama_edit)).setHint("Açıklama Giriniz: ");

            ((Button)findViewById(R.id.tip_kaydet_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String adi, aciklama;
                    adi=((EditText) findViewById(R.id.tip_adi_edit)).getText().toString();
                    aciklama=((EditText) findViewById(R.id.tip_aciklama_edit)).getText().toString();

                    if (okc.aynitipadieditvarmi(adi)){
                        Toast.makeText(TipTanimlama.this, "Bu onarım durum kayıtlı!", Toast.LENGTH_SHORT).show();
                    }else {
                        yzc.tipYaz(adi, aciklama,SabitDegiskenler.ONARIM_DURUM_TIP);
                        Toast.makeText(TipTanimlama.this, "Kayıt Başarılı.", Toast.LENGTH_SHORT).show();

                    }
                }
            });

            ((Button)findViewById(R.id.tip_guncelle_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String adi, aciklama;
                    int secilenTipId=((DegerIkilisi) mevcutTiplerSpinner.getSelectedItem()).getId();
                    adi=((EditText) findViewById(R.id.tip_adi_edit)).getText().toString();
                    aciklama=((EditText) findViewById(R.id.tip_aciklama_edit)).getText().toString();

                    if (okc.aynitipadieditvarmi(adi, secilenTipId)){
                        Toast.makeText(TipTanimlama.this, "Bu onarım durum mevcut!", Toast.LENGTH_SHORT).show();
                    }else {
                        yzc.tiptanimlamaGuncelle(adi,aciklama,secilenTipId);
                        Toast.makeText(TipTanimlama.this, "Kayıt Başarılı.", Toast.LENGTH_SHORT).show();

                    }

                    startActivity(new Intent(TipTanimlama.this, TipTanimlama.class));
                }
            });

            ((Button)findViewById(R.id.tip_sil_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int curTip= ((DegerIkilisi)mevcutTiplerSpinner.getSelectedItem()).getId();

                    AlertDialog.Builder silmeDialog = new AlertDialog.Builder(TipTanimlama.this);
                    AlertDialog silDialog;
                    silmeDialog.setMessage("Bu onarım durum tipini silmek istediğinizden emin misiniz ?")
                            .setTitle("Onarım durum silinecek!")
                            .setCancelable(false)
                            .setIcon(R.drawable.warning_24)
                            .setPositiveButton("Evet", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    //Silme işlemi bu kısma kodlanacak
                                    yzc.tiptanimlamaSil(curTip);
                                    Toast.makeText(TipTanimlama.this, "Silme İşlemi Başarılı!", Toast.LENGTH_SHORT).show();
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
        else if (tiptipi== SabitDegiskenler.YEDEK_PARCA_TIP) {

            Spinner mevcutTiplerSpinner=findViewById(R.id.mevcuttiplerspinner);
            DropDownAdapter tiplistesiadapter=new DropDownAdapter(this,okc.tipgrubunaGoreTipleriver(SabitDegiskenler.YEDEK_PARCA_TIP));
            mevcutTiplerSpinner.setAdapter(tiplistesiadapter);

            mevcutTiplerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    int secilenTipid=((DegerIkilisi) mevcutTiplerSpinner.getSelectedItem()).getId();
                    if(secilenTipid!=-1) {
                        ((EditText) findViewById(R.id.tip_adi_edit)).setText(((DegerIkilisi) mevcutTiplerSpinner.getSelectedItem()).getText());
                        DinamikTipler secilenTip = okc.idyegoretipver(secilenTipid);
                        fnk.spinnerSelector(mevcutTiplerSpinner, secilenTip.getId());
                        ((EditText) findViewById(R.id.tip_aciklama_edit)).setText(secilenTip.getAciklama());
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            ((TextView) findViewById(R.id.titleTextView)).setText("Yedek Parça Tipi Tanımlama");
            ((EditText) findViewById(R.id.tip_adi_edit)).setHint("Yedek Parça Tipi Giriniz: ");
            ((EditText) findViewById(R.id.tip_aciklama_edit)).setHint("Açıklama Giriniz: ");

            ((Button)findViewById(R.id.tip_kaydet_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String adi, aciklama;
                    adi=((EditText) findViewById(R.id.tip_adi_edit)).getText().toString();
                    aciklama=((EditText) findViewById(R.id.tip_aciklama_edit)).getText().toString();

                    if (okc.aynitipadieditvarmi(adi)){
                        Toast.makeText(TipTanimlama.this, "Bu yedek parça kayıtlı!", Toast.LENGTH_SHORT).show();
                    }else {
                        yzc.tipYaz(adi, aciklama,SabitDegiskenler.YEDEK_PARCA_TIP);
                        Toast.makeText(TipTanimlama.this, "Kayıt Başarılı.", Toast.LENGTH_SHORT).show();

                    }
                }
            });

            ((Button)findViewById(R.id.tip_guncelle_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String adi, aciklama;
                    int secilenTipId=((DegerIkilisi) mevcutTiplerSpinner.getSelectedItem()).getId();
                    adi=((EditText) findViewById(R.id.tip_adi_edit)).getText().toString();
                    aciklama=((EditText) findViewById(R.id.tip_aciklama_edit)).getText().toString();

                    if (okc.aynitipadieditvarmi(adi, secilenTipId)){
                        Toast.makeText(TipTanimlama.this, "Bu yedek parça mevcut!", Toast.LENGTH_SHORT).show();
                    }else {
                        yzc.tiptanimlamaGuncelle(adi,aciklama,secilenTipId);
                        Toast.makeText(TipTanimlama.this, "Kayıt Başarılı.", Toast.LENGTH_SHORT).show();

                    }

                    startActivity(new Intent(TipTanimlama.this, TipTanimlama.class));
                }
            });

            ((Button)findViewById(R.id.tip_sil_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int curTip= ((DegerIkilisi)mevcutTiplerSpinner.getSelectedItem()).getId();

                    AlertDialog.Builder silmeDialog = new AlertDialog.Builder(TipTanimlama.this);
                    AlertDialog silDialog;
                    silmeDialog.setMessage("Bu yedek parçayı silmek istediğinizden emin misiniz ?")
                            .setTitle("Yedek parça silinecek!")
                            .setCancelable(false)
                            .setIcon(R.drawable.warning_24)
                            .setPositiveButton("Evet", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    //Silme işlemi bu kısma kodlanacak
                                    yzc.tiptanimlamaSil(curTip);
                                    Toast.makeText(TipTanimlama.this, "Silme İşlemi Başarılı!", Toast.LENGTH_SHORT).show();
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