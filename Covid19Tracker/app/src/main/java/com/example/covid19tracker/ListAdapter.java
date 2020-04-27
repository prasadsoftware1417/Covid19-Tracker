package com.example.covid19tracker;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


public class ListAdapter extends ArrayAdapter<CountriesModel> {

    private Context context;
    private List<CountriesModel> list,filterList;
    private TextView countryName;
    private ImageView flag;

    public ListAdapter(Context context, List<CountriesModel> list) {
        super(context, R.layout.layout_list_item,list);
        this.context=context;
        this.list=list;
        this.filterList=list;
    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item, null, true);

        countryName=view.findViewById(R.id.countryName);
        flag=view.findViewById(R.id.flagImageView);

        countryName.setText(filterList.get(position).getCountry());

        Glide.with(context).load(filterList.get(position).getFlag()).into(flag);
        return view;
    }

    @Override
    public int getCount() {
        return filterList.size();
    }

    @Nullable
    @Override
    public CountriesModel getItem(int position) {
        return filterList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        Filter filter=new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults=new FilterResults();
                if(constraint==null||constraint.length()==0)
                {
                    filterResults.count=list.size();
                    filterResults.values=list;
                }
                else {
                    List<CountriesModel> result=new ArrayList<>();
                    String search=constraint.toString().toLowerCase();

                    for(CountriesModel countriesModel:list)
                    {

                        if(countriesModel.getCountry().toLowerCase().contains(search))
                        {
                            result.add(countriesModel);
                        }
                    }
                    filterResults.count=result.size();
                    filterResults.values=result;
                }
                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filterList=(List<CountriesModel>)results.values;
                AffectedContries.countryList=(List<CountriesModel>)results.values;
                notifyDataSetChanged();

            }
        };
        return filter;
    }
}
