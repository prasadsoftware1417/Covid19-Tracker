package com.example.covid19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {
    private int position;
    TextView cases,criticals,recovered,active,todayCases,todayDeaths,countryName,totalDeaths;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent=getIntent();
        position=intent.getIntExtra("position", 0);

        getSupportActionBar().setTitle("Details of "+AffectedContries.countryList.get(position).getCountry());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        cases=findViewById(R.id.tvCases);
        recovered=findViewById(R.id.tvRecovered);
        criticals=findViewById(R.id.tvCritical);
        active=findViewById(R.id.tvActive);
        todayCases=findViewById(R.id.tvTodayCases);
        todayDeaths=findViewById(R.id.tvTodayDeaths);
        totalDeaths=findViewById(R.id.tvTotalDeaths);
        countryName=findViewById(R.id.tvCountryName);

        cases.setText(AffectedContries.countryList.get(position).getCases());
        recovered.setText(AffectedContries.countryList.get(position).getRecovered());
        criticals.setText(AffectedContries.countryList.get(position).getCritical());
        active.setText(AffectedContries.countryList.get(position).getActive());
        todayCases.setText(AffectedContries.countryList.get(position).getTodayCases());
        todayDeaths.setText(AffectedContries.countryList.get(position).getTodayDeaths());
        totalDeaths.setText(AffectedContries.countryList.get(position).getDeaths());
        countryName.setText(AffectedContries.countryList.get(position).getCountry());


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
