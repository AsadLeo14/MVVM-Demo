package com.example.user.mvvmdemo.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.example.user.mvvmdemo.user.User;
import com.example.user.mvvmdemo.user.UserDao;

@Database(entities = {User.class}, version = 1)
@TypeConverters(DataTypeConverter.class)
public abstract class MyDatabase extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile MyDatabase INSTANCE;

    // --- DAO ---
    public abstract UserDao userDao();
}