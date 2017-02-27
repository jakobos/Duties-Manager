package com.dreamsfactory.dutiesmanager.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dreamsfactory.dutiesmanager.R;
import com.dreamsfactory.dutiesmanager.database.entities.Task;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Kuba on 2017-02-24.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private List<Task> tasksList;
    private Listener listener;

    public static interface Listener{
        public void onClick(int position);
    }
    public void setListener(Listener listener){
        this.listener = listener;
    }

    public HomeAdapter(List<Task> tasksList){
        this.tasksList = tasksList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Task task = tasksList.get(position);
        View view = holder.view;
        TextView nameText = (TextView) view.findViewById(R.id.homeTitle);
        TextView ownerText = (TextView) view.findViewById(R.id.homeOwner);
        TextView deadlineText = (TextView) view.findViewById(R.id.homeDeadline);

        nameText.setText(task.getTitle());
        ownerText.setText(String.valueOf(task.getOwnerId()));
        deadlineText.setText(String.valueOf(task.getDeadline()));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onClick(position);
                }
            }
        });
        //holder.name.setText(task.getTitle());
    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private View view; //and more
        public MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            //name = (TextView) itemView.findViewById(R.id.homeName);
        }
    }
}
