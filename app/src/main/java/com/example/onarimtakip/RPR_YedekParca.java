package com.example.onarimtakip;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.onarimtakip.Tanimlamalar.DegerIkilisi;
import com.example.onarimtakip.Tanimlamalar.DropDownAdapter;
import com.example.onarimtakip.Tanimlamalar.Fonksiyonlar;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Model;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Okuyucu;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.SabitDegiskenler;
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

public class RPR_YedekParca extends AppCompatActivity {
    Okuyucu okc;
    Fonksiyonlar fnk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rpr_yedek_parca);

        ((TextView) findViewById(R.id.titleTextView)).setText("Yedek Parça Rapor Sayfası");

        Yazici yzc= new Yazici(RPR_YedekParca.this);
        okc=new Okuyucu(RPR_YedekParca.this);
        fnk=new Fonksiyonlar(RPR_YedekParca.this);

        Spinner mevcutYedekParcaTiplerSpinner=findViewById(R.id.spn_yedekparcatip);
        DropDownAdapter tiplistesiadapter=new DropDownAdapter(this,okc.tipgrubunaGoreTipleriver(SabitDegiskenler.YEDEK_PARCA_TIP));
        mevcutYedekParcaTiplerSpinner.setAdapter(tiplistesiadapter);

        Spinner mevcutMarkaSpinner=findViewById(R.id.spn_parcamarka);
        DropDownAdapter markaListesiadapter=new DropDownAdapter(this,okc.markaver());
        mevcutMarkaSpinner.setAdapter(markaListesiadapter);

        mevcutYedekParcaTiplerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int secilenTipId=((DegerIkilisi) mevcutYedekParcaTiplerSpinner.getSelectedItem()).getId();
                int secilenMarkaId=((DegerIkilisi) mevcutMarkaSpinner.getSelectedItem()).getId();

                if(secilenMarkaId != -1 &&secilenTipId !=- 1) {

                    grafikDoldur(secilenTipId,secilenMarkaId);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mevcutMarkaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int secilenMarkaId=((DegerIkilisi) mevcutMarkaSpinner.getSelectedItem()).getId();
                int secilenTipId=((DegerIkilisi) mevcutYedekParcaTiplerSpinner.getSelectedItem()).getId();

                if (secilenMarkaId != -1 &&secilenTipId !=- 1){

                    grafikDoldur(secilenTipId,secilenMarkaId);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    public void grafikDoldur(int tipId,int markaId){

        BarChart barChart = (BarChart) findViewById(R.id.modelegoreparca_rapor);
        ArrayList<BarEntry> barEntries = new ArrayList<BarEntry>();
        ArrayList<Model> modelListesi = okc.markaIdyegoreModelListesiVer(markaId);

        int toplamrprdgr=0;
        for(int i=0;i<modelListesi.size();i++){
            int raporDegeri=okc.mrkMdlTipeGoreYedekParcaListesiVer(tipId,markaId,modelListesi.get(i).getId()).size();
            barEntries.add(new BarEntry(i,raporDegeri));
            toplamrprdgr+=raporDegeri;

        }
        ((TextView) findViewById(R.id.sayitextView)).setText("Toplam Parça Sayısı: " + toplamrprdgr);



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
        for (int i=0; i<modelListesi.size(); i++) {
            labels.add(modelListesi.get(i).getModel_adi());
        }
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        barChart.animateY(1000);

        barChart.getXAxis().setGranularityEnabled(true);
        barChart.getXAxis().setGranularity(1.0f);
        barChart.getXAxis().setLabelCount(barDataSet.getEntryCount());
        barChart.setData(barData);
    }
}