package com.example.christopher.recycler;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView title;
    TextView dateTime;
    TextView description;

    MyViewHolder(View view) {
        super(view);
        title = view.findViewById(R.id.name);
        dateTime = view.findViewById(R.id.empId);
        description = view.findViewById(R.id.department);
    }

}
