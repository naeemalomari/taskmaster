package com.example.taskmaster;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends  RecyclerView.Adapter<TaskAdapter.TaskViewHolder>{

    List<Tasks> allTaskData=new ArrayList<>();

    public TaskAdapter(List<Tasks> allTaskData) {
        this.allTaskData = allTaskData;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {

        public Tasks tasks;

        View itemView;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView=itemView;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("my adapter", "Element"+ getAdapterPosition()+ "clicked");
                }
            });
        }
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_tasks,parent,false);

        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {

        holder.tasks= allTaskData.get(position);
        TextView title = holder.itemView.findViewById(R.id.title28);
        TextView body = holder.itemView.findViewById(R.id.body);
        TextView state= holder.itemView.findViewById(R.id.state);

        title.setText(holder.tasks.title);
        body.setText(holder.tasks.body);
        state.setText(holder.tasks.state);

    }

    @Override
    public int getItemCount() {

        return allTaskData.size();
    }

}
