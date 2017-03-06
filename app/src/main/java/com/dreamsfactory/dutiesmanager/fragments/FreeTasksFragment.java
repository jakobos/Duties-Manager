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

import com.dreamsfactory.dutiesmanager.R;
import com.dreamsfactory.dutiesmanager.activities.FreeTaskActivity;
import com.dreamsfactory.dutiesmanager.adapters.FreeTaskAdapter;
import com.dreamsfactory.dutiesmanager.adapters.HomeAdapter;
import com.dreamsfactory.dutiesmanager.database.entities.Task;
import com.dreamsfactory.dutiesmanager.util.DividerItemDecoration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FreeTasksFragment extends Fragment {

    private RecyclerView recyclerView;
    private FreeTaskAdapter adapter;

    private List<Task> tasks;

    public FreeTasksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.freeRecyclerView);
        generateTasksList();
        adapter = new FreeTaskAdapter(tasks);
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
        adapter.setListener(new FreeTaskAdapter.Listener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), FreeTaskActivity.class);
                intent.putExtra(TaskDetailsFragment.KEY_TASK, tasks.get(position));
                startActivity(intent);
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
        tasks.add(new Task("Zadanie nr 2", getDeadline(2), 0));
        tasks.add(new Task("Zadanie nr 3", getDeadline(10), 0));
        tasks.add(new Task("Zadanie nr 4", getDeadline(4), 0));
        tasks.add(new Task("Zadanie nr 5", getDeadline(7), 0));
        tasks.add(new Task("Zadanie nr 6", getDeadline(1), 0));

    }
    private long getDeadline(int days){
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime().getTime() + days*24*60*60*1000;
    }
}
