package com.example.user.mvvmdemo.user;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.user.mvvmdemo.network.Resource;

import javax.inject.Inject;

public class UserProfileViewModel extends ViewModel {

    private LiveData<Resource<User>> user ;
    private UserRepository userRepo;

    @Inject // UserRepository parameter is provided by Dagger 2
    public UserProfileViewModel(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public void init(int userId, String sessionId) {
//        if (this.user != null) {
//            // ViewModel is created per Fragment so
//            // we know the userId won't change
//            return;
//        }

        user = userRepo.getUser(userId,sessionId);
    }

    public LiveData<Resource<User>> getProfile() {
        return user;
    }
}