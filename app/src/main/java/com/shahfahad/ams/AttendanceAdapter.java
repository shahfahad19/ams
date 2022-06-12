package com.shahfahad.ams;

import android.content.SyncRequest;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.viewHolder> {

    private String[] dates, total, present, absent, leave, absentlist;



    public AttendanceAdapter(String dates[], String total[], String present[], String absent[], String leave[], String[] absentList) {
        this.dates = dates;
        this.total = total;
        this.present = present;
        this.absent = absent;
        this.leave = leave;
        this.absentlist = absentList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.class_item,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        String dateStr = dates[position];
        String totalStr = total[position];
        String presentStr = present[position];
        String absentStr = absent[position];
        String leaveStr = leave[position];
        String absentList = absentlist[position];

        holder.dateTxt.setText(dateStr);
        holder.totalTxt.setText(totalStr);
        holder.presentTxt.setText(presentStr);
        holder.absentTxt.setText(absentStr);
        holder.leaveTxt.setText(leaveStr);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AttendanceList) v.getContext()).onClickCalled(dateStr);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dates.length;
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView dateTxt, totalTxt, presentTxt, absentTxt, leaveTxt;
        public viewHolder (View itemView) {
            super(itemView);
            dateTxt = itemView.findViewById(R.id.date);
            totalTxt = itemView.findViewById(R.id.total);
            presentTxt = itemView.findViewById(R.id.present);
            absentTxt = itemView.findViewById(R.id.absent);
            leaveTxt = itemView.findViewById(R.id.leave);
        }
    }






}

