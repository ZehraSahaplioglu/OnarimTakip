package com.example.onarimtakip;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.onarimtakip.Tanimlamalar.VeriTabani.Cihaz;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Okuyucu;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Onarim;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class RPR_Cihaz_OnrMasrafveSure extends AppCompatActivity {

    int toplamSure=0;
    double toplamMaliyet=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rpr_cihaz_onr_masrafve_sure);

        ((TextView) findViewById(R.id.titleTextView)).setText("Cihaz İçin Harcanan Toplam Maliyet ve Süre");

        Okuyucu okc=new Okuyucu(RPR_Cihaz_OnrMasrafveSure.this);
        ImageButton ara_Button = findViewById(R.id.ara_Button);


        ara_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //envno' nun yazıldığı edittext:
                String envnoText= ((EditText)findViewById(R.id.cihaz_rapor_edit)).getText().toString();
                //Cihaz sınıfından envno text'e göre liste aldık

                ArrayList<Cihaz> cihazlistesi=okc.bilgiver(envnoText);
                ArrayList<Onarim> onarimliste;

                for(int i=0; i<cihazlistesi.size() && i<30; i++) {

                    int curCihaz = cihazlistesi.get(i).getId();
                    onarimliste=okc.cihazIdyegoreOnarimListesiVer(curCihaz);

                    for (int j=0; j<onarimliste.size(); j++){
                        Onarim curOnarim=okc.idyegoreOnarimver(onarimliste.get(j).getId());

                        toplamMaliyet+=curOnarim.getToplam_maliyet();
                        toplamSure+=curOnarim.getToplam_sure();

                    }

                }

                ((TextView) findViewById(R.id.chzOnrMsrfText)).setText("Arıza Toplam Maliyet (TL): " + toplamMaliyet);
                ((TextView) findViewById(R.id.chzOnrSureText)).setText("Arıza Toplam Süre (dk): " + toplamSure);

                BarChart barChart = (BarChart) findViewById(R.id.chz_onr_msrfvesure);
                ArrayList<BarEntry> barEntries = new ArrayList<BarEntry>();

                barEntries.add(new BarEntry(0, (float) toplamMaliyet));
                barEntries.add(new BarEntry(1, toplamSure));

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
                labels.add( "Maliyet (TL)");
                labels.add( "Süre (dk)");

                barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
                barChart.animateY(1000);

                barChart.getXAxis().setGranularityEnabled(true);
                barChart.getXAxis().setGranularity(1.0f);
                barChart.getXAxis().setLabelCount(barDataSet.getEntryCount());
                barChart.setData(barData);

            }
        });

    }

}