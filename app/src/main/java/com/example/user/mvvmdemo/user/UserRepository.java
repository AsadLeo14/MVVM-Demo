package com.example.user.mvvmdemo.user;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.user.mvvmdemo.network.ApiResponse;
import com.example.user.mvvmdemo.network.NetworkBoundResource;
import com.example.user.mvvmdemo.network.Resource;
import com.example.user.mvvmdemo.network.Webservice;
import com.example.user.mvvmdemo.util.AppExecutors;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserRepository {

    private static int FRESH_TIMEOUT = 3;

    private final Webservice webservice;
    private final UserDao userDao;
    private final AppExecutors appExecutors;

    @Inject
    public UserRepository(Webservice webservice, UserDao userDao, AppExecutors appExecutors) {
        this.webservice = webservice;
        this.userDao = userDao;
        this.appExecutors = appExecutors;
    }

    public LiveData<Resource<User>> getUser(int userId, String sessionId) {
       // MediatorLiveData can observe other LiveData objects (sources) and react to their onChange events.
//        final MediatorLiveData data = new MediatorLiveData<>();
//
//        data.setValue(Resource.<User>loading(null));
//
//       webservice.getUserProfile(userId,sessionId).enqueue(new Callback<User>() {
//           @Override
//           public void onResponse(Call<User> call, Response<User> response) {
//               if(response.body().getStatus())
//               data.setValue(Resource.success(response.body()));
//               else  data.setValue(Resource.<User>error(response.body().getMessage(),null));
//           }
//
//           @Override
//           public void onFailure(Call<User> call, Throwable t) {
//               data.setValue(Resource.<User>error(t.getLocalizedMessage(),null));
//           }
//       });
//        return data;
         return loadUser(userId,sessionId);
    }

//    private void refreshUser(int userId,String sessionId) {
//        executor.execute(() -> {
//            // running in a background thread
//            // check if user was fetched recently
//            boolean userExists = userDao.hasUser(FRESH_TIMEOUT);
//            if (!userExists) {
//                // refresh the data
//                Response response = webservice.getUser(userId).execute();
//                // TODO check for error etc.
//                // Update the database.The LiveData will automatically refresh so
//                // we don't need to do anything else here besides updating the database
//                userDao.save(response.body());
//            }
//        });
//    }

    public LiveData<Resource<User>> loadUser(final int userId, final String sessionId) {
        return new NetworkBoundResource<User,User>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull User item) {
                item.setTimeStamp("9999-12-31 23:59:59");
                userDao.save(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable User data) {
                return data == null;
            }

            @NonNull
            @Override
            protected LiveData<User> loadFromDb() {
                return userDao.load();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<User>> createCall() {
                return webservice.getMyProfile(userId,sessionId);
            }
        }.asLiveData();
    }
}
