package com.example.onarimtakip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CihazKayit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cihaz_kayit);

        ((TextView) findViewById(R.id.titleTextView)).setText("Cihaz İşlemleri Sayfası");

        Button cihaz1 = findViewById(R.id.cihaz1);
        cihaz1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CihazKayit.this, YeniCihazKayit.class);
                startActivity(intent);
            }
        });

        Button cihaz2 = findViewById(R.id.cihaz2);
        cihaz2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CihazKayit.this, CihazGuncelle.class);
                startActivity(intent);
            }
        });

        Button cihaz3 = findViewById(R.id.cihaz3);
        cihaz3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CihazKayit.this, CihaziSil.class);
                startActivity(intent);
            }
        });



    }
}