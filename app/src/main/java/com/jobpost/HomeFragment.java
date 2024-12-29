package com.jobpost;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<CategoryModel> data = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Sample data
        data.add(new CategoryModel("Job listings", "See All Job Posts ", R.raw.jobs));
        data.add(new CategoryModel("Create Job listing", "Create a new Job Listing", R.raw.employer_selecting));
        data.add(new CategoryModel("My Applications", "Stored previously applied Job Applications", R.raw.applications));
        data.add(new CategoryModel("My Applicants","Stores Applicants to your Job Listing",R.raw.job_search));
        adapter = new MyAdapter(getContext(), data);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
