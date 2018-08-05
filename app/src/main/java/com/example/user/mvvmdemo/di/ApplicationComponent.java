package com.example.user.mvvmdemo.di;

import android.app.Application;

import com.example.user.mvvmdemo.user.ProfileActivity;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;


@Singleton
@Component(
        modules = {AppModule.class}
)
public interface ApplicationComponent {

    // @BindsInstance replaces Builder appModule(AppModule appModule)
    // And removes Constructor with Application AppModule(Application)
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        ApplicationComponent build();
    }
    void inject(ProfileActivity activity);
}
