package com.example.onarimtakip;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.example.onarimtakip.Tanimlamalar.VeriTabani.DinamikTipler;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Okuyucu;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Onarim;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.SabitDegiskenler;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class RPR_Ariza_Onr_Msrf extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rpr_ariza_onr_msrf);

        ((TextView) findViewById(R.id.titleTextView)).setText("Arızaya Göre Onarım Masraf Sayfası");

        BarChart barChart = (BarChart) findViewById(R.id.ariza_onrmsrf);
        ArrayList<BarEntry> barEntries = new ArrayList<BarEntry>();
        Okuyucu okc = new Okuyucu(RPR_Ariza_Onr_Msrf.this);

        ArrayList<DinamikTipler> arizaliste = okc.arizaListesiVer();
        ArrayList<Onarim> onarimliste;

        double toplamMaliyet=0;
        double ustToplamMaliyet=0;

        for (int i=0; i<arizaliste.size(); i++){
            toplamMaliyet=0;
            int curArizaId=arizaliste.get(i).getId();
            onarimliste=okc.arizatipiIdyegoreOnarimListesiVer(curArizaId);

            for (int j=0; j<onarimliste.size(); j++){
                Onarim curOnarim=okc.idyegoreOnarimver(onarimliste.get(j).getId());

                toplamMaliyet+=curOnarim.getToplam_maliyet();

            }

            ustToplamMaliyet+=toplamMaliyet;
            barEntries.add(new BarEntry((float)i,(float)toplamMaliyet));
        }

        ((TextView) findViewById(R.id.arizaOnrMsrfTextView)).setText("Ariza Toplam Maliyet (TL): " + ustToplamMaliyet);

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
        for (int i=0; i<arizaliste.size(); i++) {
            labels.add(arizaliste.get(i).getAdi());

        }
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        barChart.animateY(1000);

        barChart.getXAxis().setGranularityEnabled(true);
        barChart.getXAxis().setGranularity(1.0f);
        barChart.getXAxis().setLabelCount(barDataSet.getEntryCount());
        barChart.setData(barData);

    }
}