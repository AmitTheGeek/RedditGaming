package com.amit.redditgaming.datasource;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.amit.redditgaming.AppController;
import com.amit.redditgaming.model.Children;
import com.amit.redditgaming.model.Feed;
import com.amit.redditgaming.utils.NetworkState;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.SearchManager.QUERY;


public class FeedDataSource extends PageKeyedDataSource<String, Children> {

    private static final String TAG = FeedDataSource.class.getSimpleName();

    private AppController appController;

    private MutableLiveData networkState;
    private MutableLiveData initialLoading;

    public FeedDataSource(AppController appController) {
        this.appController = appController;

        networkState = new MutableLiveData();
        initialLoading = new MutableLiveData();
    }


    public MutableLiveData getNetworkState() {
        return networkState;
    }

    public MutableLiveData getInitialLoading() {
        return initialLoading;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params,
                            @NonNull final LoadInitialCallback<String, Children> callback) {

        initialLoading.postValue(NetworkState.LOADING);
        networkState.postValue(NetworkState.LOADING);

        appController.getRestApi().fetchFeed(QUERY, 1, params.requestedLoadSize)
                .enqueue(new Callback<Feed>() {
                    @Override
                    public void onResponse(Call<Feed> call, Response<Feed> response) {
                        if(response.isSuccessful()) {
                            System.out.println("here hereree erere here" + response.body().getData().getChildren());
                            callback.onResult(response.body().getData().getChildren(), null, response.body().getData().getAfter());
                            initialLoading.postValue(NetworkState.LOADED);
                            networkState.postValue(NetworkState.LOADED);

                        } else {
                            initialLoading.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                            networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                        }
                    }

                    @Override
                    public void onFailure(Call<Feed> call, Throwable t) {
                        String errorMessage = t == null ? "unknown error" : t.getMessage();
                        networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params,
                           @NonNull LoadCallback<String, Children> callback) {

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<String> params,
                          @NonNull final LoadCallback<String, Children> callback) {

        Log.i(TAG, "Loading Rang " + params.key + " Count " + params.requestedLoadSize);

        networkState.postValue(NetworkState.LOADING);

        appController.getRestApi().fetchFeed(QUERY, 0, params.requestedLoadSize).enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {
                if(response.isSuccessful()) {
                    String nextKey = (params.key == response.body().getData().getAfter()) ? null : params.key+1;
                    callback.onResult(response.body().getData().getChildren(), nextKey);
                    networkState.postValue(NetworkState.LOADED);

                } else networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
            }

            @Override
            public void onFailure(Call<Feed> call, Throwable t) {
                String errorMessage = t == null ? "unknown error" : t.getMessage();
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
            }
        });
    }
}
