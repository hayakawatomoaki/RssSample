package com.sample.rsssample;

import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RssListAdapter extends ArrayAdapter<Item> {
    private LayoutInflater mInflater;
    private TextView mTitle;
    private TextView mDescr;

    public RssListAdapter(Context context) {
        super(context, R.layout.item_row);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(List<Item> data) {
        clear();
        if (data != null) {
            for(Item item: data) {
                if (item == null) {
                    continue;
                }
                add(item);
            }
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (convertView == null) {
            view = mInflater.inflate(R.layout.item_row, null);
        }

        Item item = this.getItem(position);
        if (item != null) {
            String title = item.getTitle().toString();
            mTitle = (TextView) view.findViewById(R.id.item_title);
            mTitle.setText(title);
            String descr = item.getDescription().toString();
            mDescr = (TextView) view.findViewById(R.id.item_descr);
            mDescr.setText(descr);
        }
        return view;
    }
}