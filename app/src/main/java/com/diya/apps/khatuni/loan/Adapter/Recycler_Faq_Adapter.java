package com.diya.apps.khatuni.loan.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diya.apps.khatuni.R;


public class Recycler_Faq_Adapter extends RecyclerView.Adapter<View_Holder> {

    Context context;
    String[] dataSet;
    Boolean flag = false;
    public Recycler_Faq_Adapter(String[] data, Context context) {
        dataSet = data;
        this.context = context;
    }

    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.loan_cat_view, parent, false);
        View_Holder holder = new View_Holder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(final View_Holder holder, final int position) {


        try {
            holder.tvQue.setText(dataSet[position]);
            holder.tvAns.setText(ArrayCont.Ans[position]);
        }catch (Exception ex){}

        holder.Faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag==false)
                {holder.tvAns.setVisibility(View.VISIBLE);
                flag=true;
                }else
                {
                    holder.tvAns.setVisibility(View.GONE);
                    flag=false;
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return dataSet.length;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

    }


}