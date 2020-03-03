package com.amit.redditgaming.rest;
import com.amit.redditgaming.model.Feed;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestApi {

    @GET("r/gaming/top.json")
    Call<Feed> fetchFeed(@Query("q") String q,
                         @Query("page") long page,
                         @Query("pageSize") int pageSize);
}
