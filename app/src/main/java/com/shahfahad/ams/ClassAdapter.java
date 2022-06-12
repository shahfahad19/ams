package com.shahfahad.ams;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.viewHolder> {

    private String[] names, desc, std;



    public ClassAdapter(String names[], String desc[], String std[]) {
        this.names = names;
        this.desc = desc;
        this.std = std;
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
        String nameStr = names[position];
        String descrStr = desc[position];
        String stdStr = std[position];
        holder.className.setText(nameStr);
        holder.classDesc.setText(descrStr);
        holder.classStd.setText("Students: "+stdStr);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ClassesList) v.getContext()).onClickCalled(nameStr, Integer.parseInt(stdStr));
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView className, classDesc, classStd;
        public viewHolder (View itemView) {
            super(itemView);
            className = itemView.findViewById(R.id.className);
            classDesc = itemView.findViewById(R.id.description);
            classStd = itemView.findViewById(R.id.students);
        }
    }






}

