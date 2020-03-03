package com.amit.redditgaming.Activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.amit.redditgaming.AppController;
import com.amit.redditgaming.R;
import com.amit.redditgaming.adapters.FeedListAdapter;
import com.amit.redditgaming.databinding.FeedActivityBinding;
import com.amit.redditgaming.viewmodel.FeedViewModel;

public class FeedActivity extends AppCompatActivity {

    private FeedListAdapter adapter;
    private FeedViewModel feedViewModel;
    private FeedActivityBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
         * Step 1: Using DataBinding, we setup the layout for the activity
         *
         * */
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        /*
         * Step 2: Initialize the ViewModel
         *
         * */
        feedViewModel = new FeedViewModel(AppController.create(this));

        /*
         * Step 2: Setup the adapter class for the RecyclerView
         *
         * */
        binding.listFeed.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new FeedListAdapter(getApplicationContext());


        /*
         * Step 4: When a new page is available, we call submitList() method
         * of the PagedListAdapter class
         *
         * */
        feedViewModel.getRedditData().observe(this, pagedList -> {
            adapter.submitList(pagedList);
        });

        /*
         * Step 5: When a new page is available, we call submitList() method
         * of the PagedListAdapter class
         *
         * */
        feedViewModel.getNetworkState().observe(this, networkState -> {
            adapter.setNetworkState(networkState);
        });

        binding.listFeed.setAdapter(adapter);
    }
}
