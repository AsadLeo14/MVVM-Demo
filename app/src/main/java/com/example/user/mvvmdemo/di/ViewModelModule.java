package com.example.user.mvvmdemo.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.user.mvvmdemo.user.UserProfileViewModel;
import com.example.user.mvvmdemo.util.GithubViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(UserProfileViewModel.class)
    abstract ViewModel bindUserViewModel(UserProfileViewModel userProfileViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(GithubViewModelFactory factory);
}