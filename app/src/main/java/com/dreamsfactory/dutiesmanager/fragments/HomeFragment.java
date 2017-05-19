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
import com.dreamsfactory.dutiesmanager.database.services.TaskService;
import com.dreamsfactory.dutiesmanager.managers.LogManager;
import com.dreamsfactory.dutiesmanager.settings.Settings;
import com.dreamsfactory.dutiesmanager.util.DividerItemDecoration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    private RecyclerView recyclerView;
    private HomeAdapter adapter;

    private List<Task> tasks;
    private long userId;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.homeRecyclerView);
        userId = Long.valueOf(Settings.getInstance(getActivity()).get(Settings.USER_ID));

        tasks = new ArrayList<>();


        if(tasks != null){
            adapter = new HomeAdapter(tasks);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
            recyclerView.setAdapter(adapter);

        }


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        LogManager.logInfo("onStart()");
        tasks = DbManager.getInstance(getActivity()).getTaskService().getTasksByIsDone(false);

        Collections.sort(tasks, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                return ((Long)o1.getMilis()).compareTo((Long)o2.getMilis());
            }
        });

        LogManager.logInfo("tasks size = " + tasks.size());


        if(tasks != null){
            adapter.swap(tasks);
            adapter.setListener(new HomeAdapter.Listener() {
                @Override
                public void onClick(int position) {

                    if(tasks.get(position).getOwnerId() <= 0){
                        Intent intent = new Intent(getActivity(), FreeTaskActivity.class);
                        intent.putExtra(TaskDetailsFragment.KEY_TASK, tasks.get(position));
                        startActivity(intent);
                    }else if(tasks.get(position).getOwnerId() > 0 && tasks.get(position).getOwnerId() == userId){
                        Intent intent = new Intent(getActivity(), MyTaskActivity.class);
                        intent.putExtra(TaskDetailsFragment.KEY_TASK, tasks.get(position));
                        startActivity(intent);
                    }else if(tasks.get(position).getOwnerId() != userId){
                        Intent intent = new Intent(getActivity(), FriendTaskActivity.class);
                        intent.putExtra(TaskDetailsFragment.KEY_TASK, tasks.get(position));
                        startActivity(intent);
                    }

                }
            });
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if(tasks != null){
            adapter.setListener(null);
        }

    }
}
