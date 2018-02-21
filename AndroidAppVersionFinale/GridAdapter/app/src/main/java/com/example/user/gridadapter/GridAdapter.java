package com.example.user.gridadapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Meriem Chebaane
 */

public class GridAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> arrayList;

    public GridAdapter(Context context, ArrayList<String> arrayList){

        this.context=context;
        this.arrayList=arrayList;


    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null)
        {
            LayoutInflater layoutInflater= (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view=layoutInflater.inflate(R.layout.item_grid,null);
        }
        TextView name = (TextView)view.findViewById(R.id.name);
        TextView num = (TextView)view.findViewById(R.id.num);
        String s =arrayList.get(i);
        if(s.contains("/")){
            String resultats[] = s.split("\\/");
            name.setText(resultats[0]);
            num.setText(resultats[1].replace("}",""));
        }
        return view;
    }
}
