package com.example.user.mvvmdemo.di;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.support.annotation.NonNull;

import com.example.user.mvvmdemo.db.MyDatabase;
import com.example.user.mvvmdemo.network.LiveDataCallAdapterFactory;
import com.example.user.mvvmdemo.network.Webservice;
import com.example.user.mvvmdemo.user.UserDao;
import com.example.user.mvvmdemo.user.UserRepository;
import com.example.user.mvvmdemo.util.AppExecutors;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
public class AppModule {


    private String BASE_URL = "https://customproject-6a04e.firebaseio.com/";

    // --- DATABASE INJECTION ---

    @Provides
    @Singleton
    MyDatabase provideDatabase(Application application) {
        return Room.databaseBuilder(application,
                MyDatabase.class, "MyDatabase.db")
                .build();
    }

    @Provides
    @Singleton
    UserDao provideUserDao(MyDatabase database) { return database.userDao(); }

    // --- NETWORK INJECTION ---


    @Provides
    Gson provideGson() {
        return new GsonBuilder()
            .setLenient()
            .create();
    }


    @Singleton
    @Provides
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor interceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson,OkHttpClient client) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides
    @Singleton
    @NonNull
    Webservice provideApiInterface(Retrofit retrofit) {
        return retrofit.create(Webservice.class);
    }

    // --- REPOSITORY INJECTION ---

    @Provides
    @Singleton
    AppExecutors provideExecutor() {
        return new AppExecutors();
    }

    @Provides
    @Singleton
    UserRepository provideUserRepository(Webservice webservice, UserDao userDao, AppExecutors appExecutors) {
        return new UserRepository(webservice, userDao, appExecutors);
    }

}
