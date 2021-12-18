package com.example.christopher.recycler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private final List<Notes> notesList;
    private final MainActivity mainAct;

    NotesAdapter(List<Notes> empList, MainActivity ma) {
        this.notesList = empList;
        mainAct = ma;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notes_list_row, parent, false);

        itemView.setOnClickListener(mainAct);
        itemView.setOnLongClickListener(mainAct);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Notes notes = notesList.get(position);
        holder.title.setText(notes.getName());
        holder.dateTime.setText(NoteUtils.dateFromLong(notes.getDateTime()));
        holder.description.setText(NoteUtils.descriptionFormat(notes.getDescription()));
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

}