package com.dreamsfactory.dutiesmanager.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamsfactory.dutiesmanager.R;
import com.dreamsfactory.dutiesmanager.activities.FreeTaskActivity;
import com.dreamsfactory.dutiesmanager.activities.FriendTaskActivity;
import com.dreamsfactory.dutiesmanager.activities.MyTaskActivity;
import com.dreamsfactory.dutiesmanager.adapters.HomeAdapter;
import com.dreamsfactory.dutiesmanager.database.DbManager;
import com.dreamsfactory.dutiesmanager.database.entities.Task;
import com.dreamsfactory.dutiesmanager.util.DividerItemDecoration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    private RecyclerView recyclerView;
    private HomeAdapter adapter;

    private List<Task> tasks;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.homeRecyclerView);
        generateTasksList();
        adapter = new HomeAdapter(tasks);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        //tasks = //DbManager.getInstance(getContext()).getTaskService().getAllTasks();

        adapter.setListener(new HomeAdapter.Listener() {
            @Override
            public void onClick(int position) {

                if(tasks.get(position).getOwnerId() <= 0){
                    Intent intent = new Intent(getActivity(), FreeTaskActivity.class);
                    intent.putExtra(TaskDetailsFragment.KEY_TASK, tasks.get(position));
                    startActivity(intent);
                }else if(tasks.get(position).getOwnerId() > 0 && tasks.get(position).getOwnerId() == 3){
                    Intent intent = new Intent(getActivity(), MyTaskActivity.class);
                    intent.putExtra(TaskDetailsFragment.KEY_TASK, tasks.get(position));
                    startActivity(intent);
                }else if(tasks.get(position).getOwnerId() != 3){
                    Intent intent = new Intent(getActivity(), FriendTaskActivity.class);
                    intent.putExtra(TaskDetailsFragment.KEY_TASK, tasks.get(position));
                    startActivity(intent);
                }


                Toast.makeText(getActivity().getApplicationContext(), ""+position, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.setListener(null);
    }

    private void generateTasksList(){
        if(tasks == null){
            tasks = new ArrayList<>();
        }
        tasks.add(new Task("Zadanie nr 1", getDeadline(3), 0));
        tasks.add(new Task("Zadanie nr 2", getDeadline(2), 5));
        tasks.add(new Task("Zadanie nr 3", getDeadline(10), 3));
        tasks.add(new Task("Zadanie nr 4", getDeadline(4), 4));
        tasks.add(new Task("Zadanie nr 5", getDeadline(7), 3));
        tasks.add(new Task("Zadanie nr 6", getDeadline(1),112));

    }
    private long getDeadline(int days){
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime().getTime() + days*24*60*60*1000;
    }

}
