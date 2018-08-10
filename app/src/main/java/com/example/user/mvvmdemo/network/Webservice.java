package com.example.user.mvvmdemo.network;

import android.arch.lifecycle.LiveData;

import com.example.user.mvvmdemo.user.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Webservice {


    @GET("/User/{user_id}.json")
    LiveData<ApiResponse<User>>
    getMyProfile(@Path("user_id") String userId);

}
