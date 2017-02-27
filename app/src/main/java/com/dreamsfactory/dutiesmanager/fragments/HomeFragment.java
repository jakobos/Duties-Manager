package com.dreamsfactory.dutiesmanager.fragments;


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
import com.dreamsfactory.dutiesmanager.adapters.HomeAdapter;
import com.dreamsfactory.dutiesmanager.database.entities.Task;

import java.util.ArrayList;
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
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.setListener(new HomeAdapter.Listener() {
            @Override
            public void onClick(int position) {
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
        tasks.add(new Task("Zadanie nr 1", 10, 4));
        tasks.add(new Task("Zadanie nr 2", 11, 5));
        tasks.add(new Task("Zadanie nr 3", 542, 3));
        tasks.add(new Task("Zadanie nr 4", 12, 4));
        tasks.add(new Task("Zadanie nr 5", 65, 3));
        tasks.add(new Task("Zadanie nr 6", 32,112));

    }

}
