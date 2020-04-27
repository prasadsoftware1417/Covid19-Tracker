package com.example.covid19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AffectedContries extends AppCompatActivity {
    private EditText editText;
    private SimpleArcLoader arcLoader;
   private ListView listView;
    public static List<CountriesModel> countryList=new ArrayList<>();
    CountriesModel countriesModel;
    ListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affected_contries);
        editText=findViewById(R.id.editText);
        arcLoader=findViewById(R.id.arcLoader);
        listView=findViewById(R.id.listView);

        
        fetchData();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    adapter.getFilter().filter(s);
                    adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(AffectedContries.this,DetailsActivity.class).putExtra("position",position ));

            }
        });
    }

    private void fetchData() {
        String url="https://corona.lmao.ninja/v2/countries/";
        arcLoader.start();
        countryList.clear();
        StringRequest stringRequest =new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray=new JSONArray(response);

                    for (int j=0;j<jsonArray.length();j++){

                        JSONObject jsonObject=jsonArray.getJSONObject(j);

                        String countryName=jsonObject.getString("country");
                        String cases=jsonObject.getString("cases");
                        String todayCases=jsonObject.getString("todayCases");
                        String deaths=jsonObject.getString("deaths");
                        String todayDeaths=jsonObject.getString("todayDeaths");
                        String recovered=jsonObject.getString("recovered");
                        String criticals=jsonObject.getString("critical");
                        String active=jsonObject.getString("active");

                        JSONObject object=jsonObject.getJSONObject("countryInfo");
                        String flag=object.getString("flag");
                        countriesModel=new CountriesModel(flag, countryName, cases, todayCases, deaths, todayDeaths, active, recovered, criticals);
                        countryList.add(countriesModel);

                    }
                    adapter=new ListAdapter(AffectedContries.this, countryList);
                    listView.setAdapter(adapter);
                    arcLoader.stop();
                    arcLoader.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    getSupportActionBar().setTitle("Affected Countries");
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                } catch (JSONException e) {
                    adapter=new ListAdapter(AffectedContries.this, countryList);
                    listView.setAdapter(adapter);
                    arcLoader.stop();
                    arcLoader.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                }



            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                arcLoader.stop();
                arcLoader.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                Toast.makeText(AffectedContries.this, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
