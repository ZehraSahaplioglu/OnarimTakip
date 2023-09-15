package com.example.onarimtakip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.onarimtakip.Tanimlamalar.Fonksiyonlar;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Isyeri;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Marka;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Okuyucu;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Yazici;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class RPR_MarkaModelRaporu extends AppCompatActivity {

    Okuyucu okc;
    Yazici yzc;
    Fonksiyonlar fnk;

    LinearLayout anaLayout;
    LinearLayout.LayoutParams rlayouParametreleri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rpr_marka_model_raporu);

        ((TextView) findViewById(R.id.titleTextView)).setText("Marka-Model Onarım Sayfası");

        Button marka_raporbuton = findViewById(R.id.marka_raporbuton);
        marka_raporbuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RPR_MarkaModelRaporu.this, RPR_MarkaRaporu.class);
                startActivity(intent);
            }
        });

        yzc=new Yazici(RPR_MarkaModelRaporu.this);
        okc=new Okuyucu(RPR_MarkaModelRaporu.this);
        fnk=new Fonksiyonlar(RPR_MarkaModelRaporu.this);


        anaLayout=(LinearLayout) findViewById(R.id.markaListelayout);

        rlayouParametreleri=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        //layout'un içindeki edittext ve button'ların ozelliklerini burada topladık
        //agirligi fazla olan az yer kaplar
        LinearLayout.LayoutParams buttonParametreleri=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        ArrayList<Marka> markaListesi = okc.markaListesiVer();

        anaLayout.removeAllViews();

        for (int i=0; i<markaListesi.size(); i++){

            Marka curMarka= markaListesi.get(i);

            Button curButton = new Button(RPR_MarkaModelRaporu.this);
            curButton.setLayoutParams(buttonParametreleri);
            curButton.setText(markaListesi.get(i).getMarka_adi());
            curButton.setBackground(getDrawable(R.drawable.arka_plan_spinner));
            curButton.setPadding(10,15,10,15);
            curButton.setTextSize(20);

            curButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(RPR_MarkaModelRaporu.this, RPR_MarkaModelOnarimSayiRaporu.class);
                    intent.putExtra("marka_id", curMarka.getId());
                    startActivity(intent);

                }
            });

            anaLayout.addView(curButton);

        }








    }
}