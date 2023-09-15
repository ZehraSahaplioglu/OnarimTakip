package com.example.onarimtakip;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.example.onarimtakip.Tanimlamalar.VeriTabani.Isyeri;
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

public class RPR_IsyeriOnarimRapor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rpr_isyeri_onarim_rapor);

        ((TextView) findViewById(R.id.titleTextView)).setText("İşyeri İçin Onarım Rapor Sayfası");

        BarChart barChart = (BarChart) findViewById(R.id.isyeri_onr_sayi);
        ArrayList<BarEntry> barEntries = new ArrayList<BarEntry>();
        Okuyucu okc = new Okuyucu(RPR_IsyeriOnarimRapor.this);

        ArrayList<Isyeri> isyeriListesi=okc.isyeriListesiVer();

        ((TextView) findViewById(R.id.textView2)).setText("Toplam İşyeri Sayısı:  " + isyeriListesi.size());

        for(int i=0;i<isyeriListesi.size();i++){
            barEntries.add(new BarEntry(i, okc.isyeriIdyegoreOnarimListesiVer(isyeriListesi.get(i).getId()).size()));

        }

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
        for(int i=0;i<isyeriListesi.size();i++){
            labels.add(isyeriListesi.get(i).getIsyeri_adi());
        }

        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        barChart.animateY(1000);

        barChart.getXAxis().setGranularityEnabled(true);
        barChart.getXAxis().setGranularity(1.0f);
        barChart.getXAxis().setLabelCount(barDataSet.getEntryCount());
        barChart.setData(barData);

    }
}