package com.example.dovizbilgisi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.EventListener;

public class MainActivity extends AppCompatActivity {

    TextView chfText, usdText, jpyText, trText, cadText;
    Button getRates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    public void getRates(View view) {
        DownloadData downloadData = new DownloadData();
        try {
            String url = "http://data.fixer.io/api/latest?access_key=6de45dde0b707231a8cc7bc05b516158&format=1";
            downloadData.execute(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class DownloadData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            URL url;
            HttpURLConnection httpURLConnection;

            try {
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);

                int data = reader.read();

                while (data > 0) {
                    char character = (char) data;
                    result += character;
                    data = reader.read();
                }

                return result;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //System.out.println("gelen"+s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                String base = jsonObject.getString("base");
                System.out.println("Base :" + base);
                String rates = jsonObject.getString("rates");
                System.out.println("Rates : " + rates);
                JSONObject newJsonObject = new JSONObject(rates);

                String tl = newJsonObject.getString("TRY");
                String usd = newJsonObject.getString("USD");
                String cad = newJsonObject.getString("CAD");
                String chf = newJsonObject.getString("CHF");
                String jpy = newJsonObject.getString("JPY");

                cadText.setText(cadText.getText().toString() + cad);
                chfText.setText(chfText.getText().toString() + chf);
                usdText.setText(usdText.getText().toString() + usd);
                jpyText.setText(jpyText.getText().toString() + jpy);
                trText.setText(trText.getText().toString() + tl);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void initialize() {
        chfText = findViewById(R.id.chfText);
        usdText = findViewById(R.id.usdText);
        jpyText = findViewById(R.id.jpyText);
        trText = findViewById(R.id.trText);
        cadText = findViewById(R.id.cadText);
        getRates = findViewById(R.id.getRates);
    }
}
