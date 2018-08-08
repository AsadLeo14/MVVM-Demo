package com.example.user.mvvmdemo.user;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.mvvmdemo.R;
import com.example.user.mvvmdemo.network.Resource;
import com.example.user.mvvmdemo.util.CustomApplication;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.pic)
    ImageView iv;
    @BindView(R.id.name)
    TextView tv;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((CustomApplication) getApplication()).getApplicationComponent().inject(this);
        ButterKnife.bind(this);

        UserProfileViewModel viewModel = ViewModelProviders.of(this, viewModelFactory).get(UserProfileViewModel.class);
        viewModel.init(16,getString(R.string.key));
        viewModel.getProfile().observe(this, new Observer<Resource<User>>() {
            @Override
            public void onChanged(@Nullable Resource<User> userResource) {
                updateUI(userResource);
            }
        });
    }

     private void updateUI(@Nullable Resource<User> response){
            switch (response.status)
            {

                case SUCCESS:
                    if(!response.data.getDetail().getImage().isEmpty())
                    Picasso
                            .get()
                            .load(response.data.getDetail().getImage())
                            .resize(120,120)
                            .placeholder(R.drawable.placeholder)
                            .centerCrop()
                            .into(iv);
                    tv.setText(response.data.getDetail().getName());
                    break;
                case ERROR:
                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show();
                    break;

            }

        }
    }


