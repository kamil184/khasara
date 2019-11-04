package com.diya.apps.khatuni.loan.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.diya.apps.khatuni.R;


//The adapters View Holder
public class View_Holder extends RecyclerView.ViewHolder {

    TextView tvQue,tvAns;
    CardView Faq;


    public View_Holder(View itemView) {
        super(itemView);

        tvQue = (TextView)itemView.findViewById(R.id.tvQue);
        tvAns = (TextView)itemView.findViewById(R.id.tvAns);
        Faq = (CardView)itemView.findViewById(R.id.Faq);

    }


}
