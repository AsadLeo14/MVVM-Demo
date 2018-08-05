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
        viewModel.init(16,"ff3c81cc8a9d683a1f59e6278ec0d3b7");

//        viewModel.getProfile().observe(this, new Observer<ApiResponse<User>>() {
//            @Override
//            public void onChanged(@Nullable ApiResponse<User> userApiResponse) {
//                updateUI(userApiResponse);
//            }
//        });
        viewModel.getProfile().observe(this, new Observer<Resource<User>>() {
            @Override
            public void onChanged(@Nullable Resource<User> userResource) {
                updateUI(userResource);
            }
        });
    }

     private void updateUI(@Nullable Resource<User> response){
         ProgressDialog pd = new ProgressDialog(this);
            switch (response.status)
            {
                case LOADING:
//                    pd.setMessage("Please Wait");
//                    pd.show();
                case SUCCESS:
                  //  pd.hide();
                    if(null == response.data)
                        return;

                    if(!response.data.getDetail().getImage().isEmpty())
                    Picasso
                            .get()
                            .load(response.data.getDetail().getImage())
                            .resize(90,90)
                            .placeholder(R.drawable.thumbnail)
                            .into(iv);
                    tv.setText(response.data.getDetail().getName());
                    break;
                case ERROR:
                  //  pd.hide();
                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show();
                    Log.d("TAG",response.message);
                    break;

            }

        }
    }


