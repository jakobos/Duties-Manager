package com.dreamsfactory.dutiesmanager.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dreamsfactory.dutiesmanager.R;
import com.dreamsfactory.dutiesmanager.database.entities.Task;

import java.util.List;

/**
 * Created by Kuba on 2017-02-24.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private List<Task> tasksList;

    public HomeAdapter(List<Task> tasksList){
        this.tasksList = tasksList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Task task = tasksList.get(position);
        holder.name.setText(task.getTitle());
    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name; //and more
        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.homeName);
        }
    }
}
