package com.dreamsfactory.dutiesmanager.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dreamsfactory.dutiesmanager.R;
import com.dreamsfactory.dutiesmanager.database.DbManager;
import com.dreamsfactory.dutiesmanager.database.entities.Friend;
import com.dreamsfactory.dutiesmanager.database.entities.Task;
import com.dreamsfactory.dutiesmanager.database.entities.User;
import com.dreamsfactory.dutiesmanager.settings.Settings;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kuba on 2017-02-24.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private List<Task> tasksList;
    private Listener listener;
    private Context context;

    public static interface Listener{
        public void onClick(int position);
    }
    public void setListener(Listener listener){
        this.listener = listener;
    }

    public HomeAdapter(Context context, List<Task> tasksList){
        this.context = context;
        this.tasksList = tasksList;
    }

    public void swap(List<Task> data){
        tasksList.clear();
        tasksList.addAll(data);
        notifyDataSetChanged();
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
        if(task.getOwnerId() > 0){
            if(task.getOwnerId() != Long.valueOf(Settings.getInstance(context).get(Settings.USER_ID))){
                Friend friend = DbManager.getInstance(context).getFriendService().getFriend(task.getOwnerId());
                if(friend != null)
                    ownerText.setText(friend.getFriendName());
            }else{
                User user = DbManager.getInstance(context).getUserService().getUser();
                if(user != null){
                    ownerText.setText(user.getName());
                }else{
                    ownerText.setText(R.string.unknown_owner);
                }
            }

        }else{
            ownerText.setText(R.string.task_no_owner);
        }
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
