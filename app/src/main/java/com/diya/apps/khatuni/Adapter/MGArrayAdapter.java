package com.diya.apps.khatuni.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.diya.apps.khatuni.R;
import com.diya.apps.khatuni.Utils.MainItem;

import java.util.ArrayList;


public class MGArrayAdapter extends ArrayAdapter<MainItem> {

    Context context;
    int layoutResourceId;
    ArrayList<MainItem> data = new ArrayList<MainItem>();
    boolean leftGravity = false;

    public MGArrayAdapter(Context context, int layoutResourceId, ArrayList<MainItem> data) {
        super(context, layoutResourceId, data);

        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    public MGArrayAdapter(Context context, int layoutResourceId, ArrayList<MainItem> data, boolean leftGravity) {
        super(context, layoutResourceId, data);

        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        this.leftGravity = leftGravity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        RecordHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new RecordHolder();
            holder.txtTitle = (TextView) row.findViewById(R.id.item_text);
            holder.imageItem = (ImageView) row.findViewById(R.id.item_image);
            row.setTag(holder);
        } else {
            holder = (RecordHolder) row.getTag();
        }

        if (leftGravity) {
            holder.txtTitle.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        }

        if (data.size() == 0)
            return row;

        MainItem item = data.get(position);
        holder.txtTitle.setText(item.getTitle());
        holder.imageItem.setImageBitmap(item.getImage());
        return row;

    }

    static class RecordHolder {
        TextView txtTitle;
        ImageView imageItem;
    }
}



