package com.dreamsfactory.dutiesmanager.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dreamsfactory.dutiesmanager.R;
import com.dreamsfactory.dutiesmanager.database.entities.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kuba on 2017-03-04.
 */

public class MyTaskAdapter extends RecyclerView.Adapter<MyTaskAdapter.MyViewHolder>{

    private List<Task> tasksList;
    private Listener listener;

    public static interface Listener{
        public void onClick(int position);
    }
    public void setListener(Listener listener){
        this.listener = listener;
    }

    public MyTaskAdapter(List<Task> tasksList){
        this.tasksList = tasksList;
    }

    public void swap(List<Task> data){
        tasksList.clear();
        tasksList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_task_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Task task = tasksList.get(position);
        View view = holder.view;
        TextView nameText = (TextView) view.findViewById(R.id.myTitle);
        TextView deadlineText = (TextView) view.findViewById(R.id.myDeadline);

        nameText.setText(task.getTitle());
        deadlineText.setText(String.valueOf(task.getCountdown()));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private View view;
        public MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }
    }

}
