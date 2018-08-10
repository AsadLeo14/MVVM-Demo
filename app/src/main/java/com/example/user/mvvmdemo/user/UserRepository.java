package com.example.user.mvvmdemo.user;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.user.mvvmdemo.network.ApiResponse;
import com.example.user.mvvmdemo.network.NetworkBoundResource;
import com.example.user.mvvmdemo.network.Resource;
import com.example.user.mvvmdemo.network.Webservice;
import com.example.user.mvvmdemo.util.AppExecutors;
import com.example.user.mvvmdemo.util.RateLimiter;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserRepository {


    private final Webservice webservice;
    private final UserDao userDao;
    private final AppExecutors appExecutors;

    private RateLimiter<String> repoListRateLimit = new RateLimiter<>(2, TimeUnit.MINUTES);


    @Inject
    public UserRepository(Webservice webservice, UserDao userDao, AppExecutors appExecutors) {
        this.webservice = webservice;
        this.userDao = userDao;
        this.appExecutors = appExecutors;
    }

    public LiveData<Resource<User>> getUser(final String userId) {
        return new NetworkBoundResource<User,User>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull User item) {
                userDao.save(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable User data) {
                return data == null || repoListRateLimit.shouldFetch("user");
            }

            @NonNull
            @Override
            protected LiveData<User> loadFromDb() {
                return userDao.load();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<User>> createCall() {
                return webservice.getMyProfile(userId);
            }
        }.asLiveData();
    }
}
