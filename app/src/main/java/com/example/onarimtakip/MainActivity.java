package com.example.onarimtakip;

import static android.graphics.Color.RED;
import static android.graphics.Color.WHITE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onarimtakip.Tanimlamalar.DegerIkilisi;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.Okuyucu;
import com.example.onarimtakip.Tanimlamalar.VeriTabani.YedekParca;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Sayfanın sol üst kısmında görülecek olan yazı, baslik.xml ile yapıldı
        ((TextView) findViewById(R.id.titleTextView)).setText("Giriş");

        // Buton ile başka sayfaya geçme işlemi
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CihazKayit.class);
                startActivity(intent);
            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, OnarimIslemleri.class);
                startActivity(intent);
            }
        });

        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Raporlar.class);
                startActivity(intent);

                //sayfa 5 for içine yaz
                //LinearLayout l1=new LinearLayout(MainActivity.this);
                //LinearLayout ana=(LinearLayout) findViewById(R.id.activity_main);
                //ana.addView(l1);
            }
        });

        Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, IsyeriIslemleri.class);
                startActivity(intent);
            }
        });

        Button button5 = findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, YedekParcaIslemleri.class);
                startActivity(intent);
            }
        });


        Okuyucu okc = new Okuyucu(MainActivity.this);
        LinearLayout uyariLayout=(LinearLayout) findViewById(R.id.layout_uyari);
        LinearLayout.LayoutParams ulayouParametreleri=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams textviewParametreleri=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        ArrayList<YedekParca> yedekParcaListesi = okc.YedekParcaListever();

        uyariLayout.removeAllViews();
        for (int i=0; i<yedekParcaListesi.size(); i++){

            YedekParca curYedekParca = yedekParcaListesi.get(i);

            int stokMin = curYedekParca.getStokmin();
            int stokMiktar = curYedekParca.getStok_miktar();

            if (stokMiktar < stokMin){

                LinearLayout araLayout=new LinearLayout(MainActivity.this);
                araLayout.setOrientation(LinearLayout.VERTICAL);  //dikey layout
                araLayout.setLayoutParams(ulayouParametreleri);

                TextView uyariTextView=new TextView(MainActivity.this);

                uyariTextView.setText(" Uyari: " + curYedekParca.getParca_adi() + " parçasının min stok miktarı aşılmıştır! " +
                        "Kalan stok miktarı: " + curYedekParca.getStok_miktar());

                uyariTextView.setLayoutParams(textviewParametreleri);
                uyariTextView.setPadding(0,10,0,40);
                uyariTextView.setTextSize(20);
                uyariTextView.setTextColor(WHITE);
                uyariTextView.setBackground(getDrawable(R.drawable.button_shape));

                araLayout.addView(uyariTextView);
                uyariLayout.addView(araLayout);

            }

        }


    }
}