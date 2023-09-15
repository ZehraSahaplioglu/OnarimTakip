package com.example.onarimtakip;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.RED;
import static android.graphics.Color.WHITE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.onarimtakip.Tanimlamalar.VeriTabani.Cihaz;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Okuyucu;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.SabitDegiskenler;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.TipGruplari;

import java.util.ArrayList;

public class CihazGuncelle extends AppCompatActivity {

    // envno'ya göre kaydedilmis cihazların listesini veriyoruz

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cihaz_guncelle);

        ((TextView) findViewById(R.id.titleTextView)).setText("Cihaz Güncelleme Sayfası");

        Okuyucu okc=new Okuyucu(CihazGuncelle.this);
        ImageButton ara_Button = findViewById(R.id.ara_Button);

        // Burada kendimiz layout oluşturup içine edittext ve button ekliyoruz
        // edittext'lerde kaydedilmis cihazlarin bilgileri yazar
        // button'larda ise güncelle yazar ve tıklandığında GuncelleKayıt sayfasına yönlendirir

        LinearLayout anaLayout=(LinearLayout) findViewById(R.id.layout_guncelle);

        // layout'ların ozelliklerini buradaki değişkende topladık
        LinearLayout.LayoutParams hlayouParametreleri=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        //layout'un içindeki edittext ve button'ların ozelliklerini burada topladık
        //agirligi fazla olan az yer kaplar
        LinearLayout.LayoutParams buttonParametreleri=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,5);
        LinearLayout.LayoutParams textviewParametreleri=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,1);

        // Ara Button
        ara_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //envno' nun yazıldığı edittext:
                String envnoText= ((EditText)findViewById(R.id.cihaz_guncelle_edit)).getText().toString();
                //Cihaz sınıfından envno text'e göre liste aldık
                ArrayList<Cihaz> cihazlistesi=okc.bilgiver(envnoText);

                // kaydedilmiş ilk 30 cihaz bilgisini verir

                anaLayout.removeAllViews();  //layoutu yeniler
                for(int i=0; i<cihazlistesi.size() && i<30; i++){

                    Cihaz curCihaz=cihazlistesi.get(i);

                    //ara layout olusturduk ve içine bilgilerin yazacağı edittext ve guncelle butonunu yazdık
                    LinearLayout araLayout=new LinearLayout(CihazGuncelle.this);
                    araLayout.setOrientation(LinearLayout.HORIZONTAL);  //yatay layout
                    araLayout.setLayoutParams(hlayouParametreleri);
                    araLayout.setPadding(0,10, 0, 30);


                    TextView ozetTextView=new TextView(CihazGuncelle.this);
                    ImageButton guncelleButon=new ImageButton(CihazGuncelle.this);

                    guncelleButon.setBackground(getDrawable(R.drawable.update_24));

                    ozetTextView.setText(curCihaz.getEnvno()+" "+okc.idyegoremarkaver(curCihaz.getMarka_id()).getMarka_adi()+" "
                            +okc.idyegoremodelver(curCihaz.getModel_id()).getModel_adi()+" "
                    +curCihaz.getGsm_no()+" "+curCihaz.getImei_no()+" "+curCihaz.getAciklama()+" ");

                    guncelleButon.setLayoutParams(buttonParametreleri);
                    ozetTextView.setLayoutParams(textviewParametreleri);
                    ozetTextView.setTextSize(20);
                    ozetTextView.setTextColor(BLACK);
                    ozetTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    ozetTextView.setBackground(getDrawable(R.drawable.ozettext_shape));

                    //sayfamıza layout ve içindeki edittext ile button'u ekliyoruz
                    araLayout.addView(ozetTextView);
                    araLayout.addView(guncelleButon);
                    anaLayout.addView(araLayout);

                    guncelleButon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(CihazGuncelle.this, GuncelleKayit.class);
                            intent.putExtra("cihaz_id", curCihaz.getId());
                            startActivity(intent);

                        }
                    });

                }
            }
        });



    }
}