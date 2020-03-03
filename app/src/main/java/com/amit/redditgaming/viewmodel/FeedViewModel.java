package com.amit.redditgaming.viewmodel;


import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.amit.redditgaming.AppController;
import com.amit.redditgaming.datasource.FeedDataSource;
import com.amit.redditgaming.datasource.factory.FeedDataFactory;
import com.amit.redditgaming.model.Children;
import com.amit.redditgaming.utils.NetworkState;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FeedViewModel extends ViewModel {

    private Executor executor;
    private LiveData<NetworkState> networkState;
    private LiveData<PagedList<Children>> childrens;


    private AppController appController;
    public FeedViewModel(@NonNull AppController appController) {
        this.appController = appController;
        init();
    }

    private void init() {
        executor = Executors.newFixedThreadPool(5);

        FeedDataFactory feedDataFactory = new FeedDataFactory(appController);
        networkState = Transformations.switchMap(feedDataFactory.getMutableLiveData(),
                new Function<FeedDataSource, LiveData<NetworkState>>() {
                    @Override
                    public LiveData<NetworkState> apply(FeedDataSource dataSource) {
                        return dataSource.getNetworkState();
                    }
                });

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(10)
                        .setPageSize(20).build();

        childrens = (new LivePagedListBuilder(feedDataFactory, pagedListConfig))
                .setFetchExecutor(executor)
                .build();
    }


    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public LiveData<PagedList<Children>> getRedditData() {
        return childrens;
    }
}
