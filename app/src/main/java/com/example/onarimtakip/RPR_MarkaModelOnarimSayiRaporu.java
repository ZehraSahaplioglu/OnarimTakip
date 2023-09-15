package com.example.onarimtakip;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.example.onarimtakip.Tanimlamalar.DegerIkilisi;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Marka;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Okuyucu;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class RPR_MarkaModelOnarimSayiRaporu extends AppCompatActivity {

    int markaID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rpr_marka_model_onarim_sayi_raporu);

        Bundle bndl = getIntent().getExtras();
        markaID = bndl.getInt("marka_id");
        Okuyucu okc = new Okuyucu(RPR_MarkaModelOnarimSayiRaporu.this);

        Marka marka= okc.idyegoremarkaver(markaID);

        ((TextView) findViewById(R.id.titleTextView)).setText(marka.getMarka_adi() + " Markası İçin Modellere Göre Onarım");

        BarChart barChart = (BarChart) findViewById(R.id.mrk_mdl_onrsayi);
        ArrayList<BarEntry> barEntries = new ArrayList<BarEntry>();

        ArrayList<DegerIkilisi> modelListesi=okc.markaIdyegoreModelListever(markaID);

        int toplamOnarim=0;
        int onarimsayisi=0;
        for(int i=0;i<modelListesi.size();i++){

            onarimsayisi=okc.markaModelIdyegoreOnarimListesiVer(markaID,modelListesi.get(i).getId()).size();
            barEntries.add(new BarEntry(i,onarimsayisi ));
            toplamOnarim+=onarimsayisi;

        }

        ((TextView)findViewById(R.id.textView2)).setText(marka.getMarka_adi()+" Markasına Ait Toplam Onarım Sayısı: " + toplamOnarim);

        BarDataSet barDataSet = new BarDataSet(barEntries, "");
        barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setHighlightEnabled(true);
        barDataSet.setHighLightColor(Color.RED);
        barDataSet.setValueTextSize(16f);
        barDataSet.setValueTextColor(Color.WHITE);

        BarData barData = new BarData(barDataSet);

        barChart.getDescription().setText("");
        barChart.getDescription().setTextSize(12);
        barChart.getAxisLeft().setAxisMinimum(0);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setTextSize(16f);
        barChart.getXAxis().setTextColor(Color.WHITE);

        ArrayList<String> labels = new ArrayList<String> ();
        for(int i=0;i<modelListesi.size();i++){
            labels.add(modelListesi.get(i).getText());
        }

        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        barChart.animateY(1000);

        barChart.getXAxis().setGranularityEnabled(true);
        barChart.getXAxis().setGranularity(1.0f);
        barChart.getXAxis().setLabelCount(barDataSet.getEntryCount());
        barChart.setData(barData);

    }
}