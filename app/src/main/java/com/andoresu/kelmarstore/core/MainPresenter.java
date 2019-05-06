package com.andoresu.kelmarstore.core;

import android.content.Context;
import android.support.annotation.NonNull;

import static com.andoresu.kelmarstore.utils.BaseFragment.BASE_TAG;

public class MainPresenter implements MainContract.ActionsListener{


    private String TAG = BASE_TAG + MainPresenter.class.getSimpleName();

    private final MainContract.View mainView;

    private final Context context;

    public MainPresenter(@NonNull MainContract.View mainView, @NonNull Context context, String authToken){
        this.mainView = mainView;
        this.context = context;

    }

    @Override
    public void logout() {}

}
