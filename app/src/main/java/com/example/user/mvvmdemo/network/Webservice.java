package com.example.user.mvvmdemo.network;

import android.arch.lifecycle.LiveData;

import com.example.user.mvvmdemo.user.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Webservice {

        @GET("myprofile.php")
        Call<User>
        getUserProfile(
            @Query("user_id") int user_id,
            @Query("session_id") String session_id);


    @GET("myprofile.php")
    LiveData<ApiResponse<User>>
    getMyProfile(
            @Query("user_id") int user_id,
            @Query("session_id") String session_id);

}
