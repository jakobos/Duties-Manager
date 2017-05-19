package com.dreamsfactory.dutiesmanager.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dreamsfactory.dutiesmanager.R;
import com.dreamsfactory.dutiesmanager.activities.MyTaskActivity;
import com.dreamsfactory.dutiesmanager.adapters.HomeAdapter;
import com.dreamsfactory.dutiesmanager.adapters.MyTaskAdapter;
import com.dreamsfactory.dutiesmanager.database.DbManager;
import com.dreamsfactory.dutiesmanager.database.entities.Task;
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
public class MyTasksFragment extends Fragment {

    private RecyclerView recyclerView;
    private MyTaskAdapter adapter;

    private List<Task> tasks;

    public MyTasksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_tasks, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.myTasksRecyclerView);
        tasks = new ArrayList<>();
        if(tasks != null){
            adapter = new MyTaskAdapter(tasks);
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
        tasks = DbManager.getInstance(getActivity()).getTaskService().getTasksByUserId(Long.valueOf(Settings.getInstance(getActivity()).get(Settings.USER_ID)));
        Collections.sort(tasks, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                return ((Long)o1.getMilis()).compareTo((Long)o2.getMilis());
            }
        });
        if(tasks != null){
            adapter.swap(tasks);
            adapter.setListener(new MyTaskAdapter.Listener() {
                @Override
                public void onClick(int position) {
                    Intent intent = new Intent(getActivity(), MyTaskActivity.class);
                    intent.putExtra(TaskDetailsFragment.KEY_TASK, tasks.get(position));
                    startActivity(intent);
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
