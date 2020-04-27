package com.example.covid19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView cases,criticals,recovered,active,todayCases,todayDeaths,affectedCountries,totalDeaths;
    ScrollView scrollView1;
    SimpleArcLoader arcLoader;
    PieChart pieChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cases=findViewById(R.id.tvCases);
        recovered=findViewById(R.id.tvRecovered);
        criticals=findViewById(R.id.tvCritical);
        active=findViewById(R.id.tvActive);
        todayCases=findViewById(R.id.tvTodayCases);
        todayDeaths=findViewById(R.id.tvTodayDeaths);
        totalDeaths=findViewById(R.id.tvTotalDeaths);
        affectedCountries=findViewById(R.id.tvAffectedCountries);
        scrollView1=findViewById(R.id.scrollView);
        pieChart=findViewById(R.id.pieChart);
        arcLoader=findViewById(R.id.loader);

        fetchData();

    }

    private void fetchData() {
        String url="https://corona.lmao.ninja/v2/all/";
        arcLoader.start();
        StringRequest stringRequest =new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject=new JSONObject(response);

                    cases.setText(jsonObject.getString("cases"));
                    recovered.setText(jsonObject.getString("recovered"));
                    criticals.setText(jsonObject.getString("critical"));
                    active.setText(jsonObject.getString("active"));
                    todayCases.setText(jsonObject.getString("todayCases"));
                    totalDeaths.setText(jsonObject.getString("deaths"));
                    todayDeaths.setText(jsonObject.getString("todayDeaths"));
                    affectedCountries.setText(jsonObject.getString("affectedCountries"));

                    pieChart.addPieSlice(new PieModel("Cases",Integer.parseInt(cases.getText().toString()), Color.parseColor("#ffA726")));
                    pieChart.addPieSlice(new PieModel("Recovered",Integer.parseInt(recovered.getText().toString()), Color.parseColor("#66BB6A")));
                    pieChart.addPieSlice(new PieModel("Deaths",Integer.parseInt(totalDeaths.getText().toString()), Color.parseColor("#Ef535e")));
                    pieChart.addPieSlice(new PieModel("Active",Integer.parseInt(active.getText().toString()), Color.parseColor("#29B6f6")));

                    pieChart.startAnimation();

                    arcLoader.stop();
                    arcLoader.setVisibility(View.GONE);

                    scrollView1.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {

                arcLoader.stop();
                arcLoader.setVisibility(View.GONE);
                scrollView1.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public void goTrackCountries(View view) {
        startActivity(new Intent(MainActivity.this,AffectedContries.class));
    }
}
