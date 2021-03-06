package com.example.taskmaster;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Task;

import java.util.List;


public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private final List<Task> taskItems;
    private OnTaskItemClickListener listener;


    public TaskAdapter(List<Task> taskMasterItem, OnTaskItemClickListener listener) {
        this.taskItems = taskMasterItem;
        this.listener = listener;
    }


    public interface OnTaskItemClickListener {
        void onItemClicked(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_tasks, parent,false);

        return new ViewHolder(view, listener);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task item = taskItems.get(position);
        holder.title.setText(item.getTitle());
        holder.body.setText(item.getBody());
        holder.state.setText(item.getState());
    }

    @Override
    public int getItemCount() {
        return taskItems.size();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private TextView body;
        private TextView state ;
        ViewHolder(@NonNull View itemView, OnTaskItemClickListener listener){
            super(itemView);

            title = itemView.findViewById(R.id.title28);
            body = itemView.findViewById(R.id.body);
            state = itemView.findViewById(R.id.state);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClicked(getAdapterPosition());

                }
            });
        }

    }

}